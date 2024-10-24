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
        List<BookingDetail> bookingDetails = bookingDetailRepository.findByBookingId(booking.getBooking_id());

        // Biến kiểm tra xem đã có bản ghi transaction_detail cho giao dịch đã xác nhận chưa
        boolean hasConfirmedTransactionDetail = false;

        // Duyệt qua từng bookingDetail để kiểm tra trạng thái và tạo bản ghi tương ứng trong transaction_detail
        for (BookingDetail bookingDetail : bookingDetails) {
            String transactionType = determineTransactionType(bookingDetail.getStatus());

            // Nếu trạng thái là CONFIRM và chưa có bản ghi transaction_detail thì tạo mới
            if ("CONFIRM".equals(bookingDetail.getStatus()) && !hasConfirmedTransactionDetail) {
                TransactionDetail transactionDetail = TransactionDetail.builder()
                        .transaction_detail_id(GenerateTransactionDetailId())
                        .transaction_id(transaction)
                        .transaction_type("PAYMENT")
                        .description(generateDescription("PAYMENT"))
                        .amount(booking.getTotal()) // Tổng số tiền của cả booking
                        .bank_name(" ")
                        .bank_account_number(" ")
                        .payment_status("Success")
                        .transaction_date(LocalDateTime.now())
                        .build();
                transactionDetailRepository.save(transactionDetail);
                hasConfirmedTransactionDetail = true; // Đánh dấu đã tạo bản ghi cho giao dịch đã xác nhận
            }

            // Chỉ tạo transaction detail nếu là dịch vụ mới
            if ("ADD_SERVICE".equals(transactionType)) {
                TransactionDetail transactionDetail = TransactionDetail.builder()
                        .transaction_detail_id(GenerateTransactionDetailId())
                        .transaction_id(transaction)
                        .transaction_type(transactionType)
                        .description(generateDescription(transactionType))
                        .amount(calculateAmount(booking, bookingDetail, transactionType))
                        .bank_name(" ")
                        .bank_account_number(" ")
                        .payment_status("Success")
                        .transaction_date(LocalDateTime.now())
                        .build();
                transactionDetailRepository.save(transactionDetail);
            }
        }
        return TransactionDetailResponse.builder()
                .transaction_detail_id(GenerateTransactionDetailId())
                .transaction_id(transaction.getTransaction_id())
                .transaction_type("Payment")  // Mặc định là thanh toán
                .description("Payment for deposit and services")
                .amount(booking.getTotal())   // Tổng số tiền của cả booking
                .payment_status("Success")
                .transaction_date(LocalDateTime.now())
                .build();
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

    private double calculateAmount(Booking booking, BookingDetail bookingDetail, String transactionType) {
        if ("CHECK IN".equals(transactionType) || "CHECK OUT".equals(transactionType)) {
            return 0.0;
        }
        String currentBookingVersion = bookingDetail.getBookingVersion();
        if ("SERVICE".equals(bookingDetail.getBooking_type()) && bookingDetail.getBookingVersion().equals(currentBookingVersion)) {
            return bookingDetail.getTotal_price();
        }
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
    private String GenerateTransactionDetailId(){
        String id = transactionDetailRepository.findLastId();
        if(!(id == null)){
            int number = Integer.parseInt(id.substring(3))+1;
            return String.format("TD-%02d", number);
        }
        return "TD-01";
    }
}
