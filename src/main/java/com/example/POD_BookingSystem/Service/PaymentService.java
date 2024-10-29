package com.example.POD_BookingSystem.Service;

import com.example.POD_BookingSystem.DTO.Request.Booking.CreateBookingDetailRequest;
import com.example.POD_BookingSystem.DTO.Response.PaymentDetailResponse;
import com.example.POD_BookingSystem.DTO.Response.TransactionDetailResponse;
import com.example.POD_BookingSystem.Entity.EBooking.Booking;
import com.example.POD_BookingSystem.Entity.EBooking.BookingDetail;
import com.example.POD_BookingSystem.Entity.EPayment.Payment;
import com.example.POD_BookingSystem.Entity.EPayment.PaymentDetail;
import com.example.POD_BookingSystem.Entity.ERoom.Room;
import com.example.POD_BookingSystem.Entity.ETransaction.Transaction;
import com.example.POD_BookingSystem.Entity.ETransaction.TransactionDetail;
import com.example.POD_BookingSystem.Entity.User;
import com.example.POD_BookingSystem.Exception.AppException;
import com.example.POD_BookingSystem.Exception.ErrorCode;
import com.example.POD_BookingSystem.Repository.ReBooking.BookingDetailRepository;
import com.example.POD_BookingSystem.Repository.RePayment.PaymentDetailRepository;
import com.example.POD_BookingSystem.Repository.RePayment.PaymentRepository;
import com.example.POD_BookingSystem.Repository.ReRoom.RoomRepository;
import com.example.POD_BookingSystem.Repository.ReTransaction.TransactionDetailRepository;
import com.example.POD_BookingSystem.Repository.ReTransaction.TransactionRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class PaymentService {
    @Autowired
    PaymentRepository paymentRepository;
    @Autowired
    PaymentDetailRepository paymentDetailRepository;
    @Autowired
    RoomRepository roomRepository;
    @Autowired
    BookingDetailRepository bookingDetailRepository;
    @Autowired
    TransactionService transactionService;
    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    TransactionDetailRepository transactionDetailRepository;
    public Payment createPayment(Booking booking){
        Payment payment = Payment.builder()
                .payment_id(GeneratePaymentId())
                .booking_id(booking)
                .total_amount(booking.getTotal())
                .build();
        paymentRepository.save(payment);
        return payment;

    }
    public List<PaymentDetailResponse> createPaymentDetail(Booking booking) {
        List<BookingDetail> bookingDetails = bookingDetailRepository.findByBookingId(booking.getBooking_id());
        if (bookingDetails.isEmpty()) throw new RuntimeException("No booking details found for the given room");
        log.info("paymentdetail: 1");
        List<PaymentDetailResponse> paymentDetailResponses = new ArrayList<>();

        for (BookingDetail bookingDetail : bookingDetails) {
            Room room = roomRepository.findById(bookingDetail.getRoom().getRoom_id())
                    .orElseThrow(() -> new RuntimeException("Room does not exist"));
            String itemType = determineItemType(bookingDetail.getBooking_type());
            String itemDescription = determineItemDescription(itemType);

            PaymentDetail paymentDetail = PaymentDetail.builder()
                    .payment_detail_id(GeneratePaymentDetailId())
                    .payment_id(paymentRepository.findBookingId(booking.getBooking_id()))
                    .item_type(itemType)
                    .room(room)
                    .service_id(bookingDetail.getService_id())
                    .item_description(itemDescription)
                    .quantity(bookingDetail.getQuantity())
                    .price(bookingDetail.getTotal_price())
                    .payment_status("Success")
                    .payment_date(LocalDateTime.now())
                    .payment_method("E-Wallet")
                    .build();

            paymentDetailRepository.save(paymentDetail);
            log.info("paymentdetail: 2");

            PaymentDetailResponse paymentDetailResponse= PaymentDetailResponse.builder()
                    .payment_detail_id(paymentDetail.getPayment_detail_id())
                    .payment_id(paymentDetail.getPayment_id().getPayment_id())
                    .item_type(paymentDetail.getItem_type())
                    .room_id(room.getRoom_id())
                    .service_id(paymentDetail.getService_id())
                    .item_description(paymentDetail.getItem_description())
                    .quantity(String.valueOf(paymentDetail.getQuantity()))
                    .price(paymentDetail.getPrice())
                    .payment_status(paymentDetail.getPayment_status())
                    .payment_date(paymentDetail.getPayment_date())
                    .payment_method(paymentDetail.getPayment_method())
                    .build();
            paymentDetailResponses.add(paymentDetailResponse);
        }
        log.info("paymentdetail: 3");

        return paymentDetailResponses;

    }



    private String determineItemType(String bookingType) {
        switch (bookingType) {
            case "SERVICE":
                return "Service";
            case "ROOM":
                return "Room Amount";

            default:
                return null;
        }
    }


    private String determineItemDescription(String itemType) {
        switch (itemType) {
            case "Room Amount":
                return "Pay for room amount";
            case "Service":
                return "Pay for service";
            default:
                return null;
        }
    }

    private String GeneratePaymentId(){
        String id = paymentRepository.findLastId();
        if(!(id == null)){
            int number = Integer.parseInt(id.substring(2))+1;
            return String.format("P-%02d", number);
        }
        return "P-01";
    }
    private String GeneratePaymentDetailId(){
        String id = paymentDetailRepository.findLastId();
        if(!(id == null)){
            int number = Integer.parseInt(id.substring(3))+1;
            return String.format("PD-%02d", number);
        }
        return "PD-01";
    }
// dang lam
    public void confirmPayment(Booking booking, double compensationAmount) {

        Payment payment = paymentRepository.findBookingId(booking.getBooking_id());
        if (payment == null) {
            payment = createPayment(booking);
        }

        //tạo bản ghi mới trong PaymentDetail
        PaymentDetail paymentDetail = PaymentDetail.builder()
                .payment_detail_id(GeneratePaymentDetailId())
                .payment_id(payment)
                .item_type("Compensation")
                .item_description("Compensation Payment")
                .quantity(1)
                .price(compensationAmount)
                .payment_status("Success")
                .payment_date(LocalDateTime.now())
                .payment_method("E-Wallet")
                .build();

        paymentDetailRepository.save(paymentDetail);

        //tạo bản ghi mới trong TransactionDetail
        Transaction transaction = transactionRepository.findBookingId(booking.getBooking_id());
        if (transaction == null) {
            transaction = transactionService.createTransaction(booking, booking.getUser());
        }

        TransactionDetail transactionDetail = TransactionDetail.builder()
                .transaction_detail_id(transactionService.GenerateTransactionDetailId())
                .transaction_id(transaction)
                .transaction_type("Compensation")
                .description("Compensation Transaction")
                .amount(compensationAmount)
                .payment_status("Success")
                .transaction_date(LocalDateTime.now())
                .build();

        transactionDetailRepository.save(transactionDetail);

        // Bước 4: Cập nhật total_amount của Payment
        payment.setTotal_amount(payment.getTotal_amount() + compensationAmount);
        paymentRepository.save(payment);

        log.info("Compensation payment confirmed for booking ID: {}", booking.getBooking_id());
    }
}
