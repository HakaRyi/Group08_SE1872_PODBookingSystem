package com.example.POD_BookingSystem.Controller;

import com.example.POD_BookingSystem.DTO.Request.Booking.*;
import com.example.POD_BookingSystem.DTO.Request.Service.AddServiceToBookingRequest;
import com.example.POD_BookingSystem.DTO.Response.*;
import com.example.POD_BookingSystem.Entity.EBooking.Booking;
import com.example.POD_BookingSystem.Repository.ReBooking.BookingRepository;
import com.example.POD_BookingSystem.Repository.ReRoom.RoomRepository;
import com.example.POD_BookingSystem.Service.AuthenticationService;
import com.example.POD_BookingSystem.Service.BookingService;
import com.nimbusds.jose.JOSEException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@Slf4j
@RestController
public class BookingController {

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    BookingService bookingService;

    @GetMapping("/{bookingId}/bookingInfo")
    ApiResponse<BookingInformationResponse> getBookingInformation(@PathVariable String bookingId){
        return ApiResponse.<BookingInformationResponse>builder()
                .data(bookingService.getBookingInformation(bookingId))
                .build();
    }

    @GetMapping("/booking")
    ApiResponse<List<BookingResponse>> getBookingByUserName(HttpServletRequest request) throws ParseException, JOSEException {
        //Lay username tu JWT token
        String token = request.getHeader("Authorization").substring(7); // Bỏ qua "Bearer " trong token
        String username = authenticationService.getUsernameFromToken(token);
        return ApiResponse.<List<BookingResponse>>builder()
                .data(bookingService.getBookingByUsername(username))
                .build();
    }

    @PostMapping("/{roomName}/bookingdetails")
    ApiResponse<BookingDetailResponse> createBookingDetail
            (@PathVariable String roomName,
             @RequestBody CreateBookingDetailRequest request,
              HttpServletRequest req  ) throws ParseException, JOSEException {
        String token = req.getHeader("Authorization").substring(7); // Bỏ qua "Bearer " trong token
        String username = authenticationService.getUsernameFromToken(token);
        return ApiResponse.<BookingDetailResponse>builder()
                .data(bookingService.createBookingDetail(username,roomName,request))
                .build();
    }

    @PostMapping("/{bookingId}/{roomName}/addServices")
    ApiResponse<BookingDetailResponse> addServices
            (@PathVariable String bookingId, @PathVariable String roomName, @RequestBody AddServiceToBookingRequest request){
        return ApiResponse.<BookingDetailResponse>builder()
                .data(bookingService.addServiceToBooking(request,bookingId,roomName))
                .build();
    }

    @PostMapping("/{bookingId}/confirm")
    ApiResponse<Void> confirmBooking(@PathVariable String bookingId, @RequestBody ConfirmRequest request){
        bookingService.confirmBooking(bookingId, request);
        return ApiResponse.<Void>builder().message("Book SuccessFully !!!").build();
    }

    @PostMapping("/{bookingId}/cancelPayment")
    ApiResponse<Void> cancelPayment(@RequestBody CancelPaymentRequest request, @PathVariable String bookingId){
        bookingService.cancelPayment(request.getAmount(), bookingId);
        return ApiResponse.<Void>builder()
                .message("Cancel Successfully")
                .build();
    }

@PostMapping("/request-checkin/{bookingId}")
ApiResponse<String> requestCheckin(@PathVariable String bookingId) {
    bookingService.requestCheckin(bookingId);
    return ApiResponse.<String>builder()
            .data("Request submitted successfully")
            .build();

}
    @PostMapping("/approve-checkin/{bookingId}")
    ApiResponse<String> approveCheckin(@PathVariable String bookingId){
        bookingService.approveCheckin(bookingId);
        return ApiResponse.<String>builder()
                .data("Check-in approved successfully")
                .build();

    }
    @PostMapping("/reject-checkin/{bookingId}")
    ApiResponse<String> rejectCheckin(@PathVariable String bookingId){
        bookingService.rejectCheckin(bookingId);
        return ApiResponse.<String>builder()
                .data("Check-in rejected successfully")
                .build();

    }

    @PostMapping("/request-checkout/{bookingId}")
    ApiResponse<String> requestCheckout(@PathVariable String bookingId) {
        bookingService.requestCheckout(bookingId);
        return ApiResponse.<String>builder()
                .data("Request submitted successfully")
                .build();

    }
    @PostMapping("/approve-checkout/{bookingId}")
    ApiResponse<String> approveCheckout(@PathVariable String bookingId){
        bookingService.approveCheckout(bookingId);
        return ApiResponse.<String>builder()
                .data("Check-out approved successfully")
                .build();

    }
    @PostMapping("/reject-checkout/{bookingId}")
    ApiResponse<String> rejectCheckout(@PathVariable String bookingId){
        bookingService.rejectCheckout(bookingId);
        return ApiResponse.<String>builder()
                .data("Check-out rejected successfully")
                .build();

    }

    @PostMapping("book/by-month")
    ApiResponse<MonthBookingResponse> createBookingByMonth(@RequestBody CreateMonthBookingRequest request){
        return ApiResponse.<MonthBookingResponse>builder()
                .data(bookingService.createMonthBookingDetail(request))
                .message("Booking SuccessFull")
                .build();
    }

}
