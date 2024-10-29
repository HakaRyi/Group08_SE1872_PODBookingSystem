package com.example.POD_BookingSystem.Service;

import com.example.POD_BookingSystem.DTO.Response.BookingDetailResponse;
import com.example.POD_BookingSystem.DTO.Response.TransactionDetailResponse;
import com.example.POD_BookingSystem.Entity.EBooking.Booking;
import com.example.POD_BookingSystem.Entity.EBooking.BookingDetail;
import com.example.POD_BookingSystem.Entity.ETransaction.Transaction;
import com.example.POD_BookingSystem.Entity.ETransaction.TransactionDetail;
import com.example.POD_BookingSystem.Entity.User;
import com.example.POD_BookingSystem.Exception.AppException;
import com.example.POD_BookingSystem.Exception.ErrorCode;
import com.example.POD_BookingSystem.Repository.ReBooking.BookingDetailRepository;
import com.example.POD_BookingSystem.Repository.ReBooking.BookingRepository;
import com.example.POD_BookingSystem.Repository.ReTransaction.TransactionDetailRepository;
import com.example.POD_BookingSystem.Repository.ReTransaction.TransactionRepository;
import com.example.POD_BookingSystem.Repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class TransactionService {
    @Autowired
    private BookingDetailRepository bookingDetailRepository;
    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    TransactionDetailRepository transactionDetailRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    BookingRepository bookingRepository;



    public Transaction createTransaction(Booking booking, User user){
        log.info("123");

        if(user == null )
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        Transaction transaction = Transaction.builder()
                .transaction_id(GenerateTransactionId())
                .booking_id(booking)
                .user_id(user)
                .build();
         transactionRepository.save(transaction);
         return transaction;

    }



    public TransactionDetailResponse createTransactionDetail(Booking booking){

        Transaction transaction = transactionRepository.findBookingId(booking.getBooking_id());

        String transactionType = determineTransactionType(booking.getStatus());
        double amount = calculateAmount(booking, transactionType); // Calculate amount

        if (transactionType != null) {
            saveTransactionDetail(transaction, transactionType, generateDescription(transactionType), amount);
        }
        return TransactionDetailResponse.builder()
                .transaction_detail_id(GenerateTransactionDetailId())
                .transaction_id(transaction.getTransaction_id())
                .transaction_type("Payment")  // Mặc định là thanh toán
                .description("Payment for deposit and services")
                .amount(amount)   // Tổng số tiền của cả booking
                .payment_status("Success")
                .transaction_date(LocalDateTime.now())
                .build();
    }

    private void saveTransactionDetail(Transaction transaction, String transactionType, String description, double amount) {
        TransactionDetail transactionDetail = TransactionDetail.builder()
                .transaction_detail_id(GenerateTransactionDetailId())
                .transaction_id(transaction)
                .transaction_type(transactionType)
                .description(description)
                .amount(amount)
                .bank_name(" ")
                .bank_account_number(" ")
                .payment_status("Success")
                .transaction_date(LocalDateTime.now())
                .build();
        transactionDetailRepository.save(transactionDetail);
    }

    // Xác định loại giao dịch (Payment, Checkin, Checkout, Add Service)
    private String determineTransactionType(String bookingDetailStatus) {
        switch (bookingDetailStatus) {
            case "CONFIRM":
                return "PAYMENT";
            case "CHECK_IN":
                return "CHECK IN";
            case "CHECK_OUT":
                return "CHECK OUT";
            case "ADD_SERVICE":
                return "ADD SERVICE";
            default:
                return null;
        }
    }
    // Tạo mô tả tương ứng cho loại giao dịch
    private String generateDescription(String transactionType) {
        switch (transactionType) {
            case "PAYMENT":
                return "Payment for deposit and services";
            case "CHECK IN":
                return "Checkin at the property";
            case "CHECK OUT":
                return "Checkout from the property";
            case "ADD SERVICE":
                return "Additional service added";
            default:
                return "Transaction";
        }
    }

    private double calculateAmount(Booking booking, String transactionType) {
        if ("CHECK IN".equals(transactionType) || "CHECK OUT".equals(transactionType)) {
            return 0.0;
        }
//        String currentBookingVersion = bookingDetail.getBookingVersion();
//        if ("SERVICE".equals(bookingDetail.getBooking_type()) && bookingDetail.getBookingVersion().equals(currentBookingVersion)) {
//            return bookingDetail.getTotal_price();
//        }
        return booking.getTotal();
    }

    private String GenerateTransactionId(){
        String id = transactionRepository.findLastId();
        if(!(id == null)){
            int number = Integer.parseInt(id.substring(2))+1;
            return String.format("T-%02d", number);
        }
        return "T-01";
    }
    public String GenerateTransactionDetailId(){
        String id = transactionDetailRepository.findLastId();
        if(!(id == null)){
            int number = Integer.parseInt(id.substring(3))+1;
            return String.format("TD-%02d", number);
        }
        return "TD-01";
    }
}
