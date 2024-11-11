//package com.example.POD_BookingSystem.Service;
//import com.example.POD_BookingSystem.DTO.Response.BookingResponse;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.messaging.simp.SimpMessagingTemplate;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class NotificationService {
//
//    @Autowired
//    private SimpMessagingTemplate messagingTemplate;
//
//    @Autowired
//    private BookingService bookingService;
//
//    // Định kỳ gửi dữ liệu checkin mỗi 30 giây
//    @Scheduled(fixedRate = 30000)
//    public void sendCheckinUpdates() {
//        List<BookingResponse> checkinData = bookingService.getCheckin();
//        messagingTemplate.convertAndSend("/topic/checkin-updates", checkinData);
//    }
//
//    // Định kỳ gửi dữ liệu checkout mỗi 30 giây
//    @Scheduled(fixedRate = 30000)
//    public void sendCheckoutUpdates() {
//        List<BookingResponse> checkoutData = bookingService.getCheckout();
//        messagingTemplate.convertAndSend("/topic/checkout-updates", checkoutData);
//    }
//}
//
