package com.example.POD_BookingSystem.Controller;

import com.example.POD_BookingSystem.DTO.Request.Booking.CreateBookingDetailRequest;
import com.example.POD_BookingSystem.DTO.Response.ApiResponse;
import com.example.POD_BookingSystem.DTO.Response.BookingDetailResponse;
import com.example.POD_BookingSystem.DTO.Response.BookingInformationResponse;
import com.example.POD_BookingSystem.DTO.Response.BookingResponse;
import com.example.POD_BookingSystem.Entity.EBooking.Booking;
import com.example.POD_BookingSystem.Service.AuthenticationService;
import com.example.POD_BookingSystem.Service.BookingService;
import com.nimbusds.jose.JOSEException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
public class BookingController {

    @Autowired
    BookingService bookingService;

    @Autowired
    AuthenticationService authenticationService;



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

//    @PostMapping("/book")
//    Booking createBooking (HttpServletRequest request) throws ParseException, JOSEException {
//        //Lay username tu JWT token
//        String token = request.getHeader("Authorization").substring(7); // Bỏ qua "Bearer " trong token
//        String username = authenticationService.getUsernameFromToken(token);
//        return bookingService.createBooking(username);
//    }

    @PostMapping("/book")
    ApiResponse<BookingResponse> createBooking (HttpServletRequest request) throws ParseException, JOSEException {
        //Lay username tu JWT token
        String token = request.getHeader("Authorization").substring(7); // Bỏ qua "Bearer " trong token
        String username = authenticationService.getUsernameFromToken(token);

        return ApiResponse.<BookingResponse>builder()
                .data(bookingService.createBooking(username))
                .build();

    }

    @PostMapping("/{bookingId}/{roomName}/bookingdetails")
    ApiResponse<BookingDetailResponse> createBookingDetail
            (@PathVariable String bookingId, @PathVariable String roomName, @RequestBody CreateBookingDetailRequest request){
        return ApiResponse.<BookingDetailResponse>builder()
                .data(bookingService.createBookingDetail(bookingId,roomName,request))
                .build();
    }

    @PostMapping("/{bookingId}/confirm")
    ApiResponse<Void> confirmBooking(@PathVariable String bookingId){
        bookingService.confirmBooking(bookingId);
        return ApiResponse.<Void>builder().message("Book SuccessFully !!!").build();
    }

//    @PostMapping({"/{}/booking"})
//    public ResponseEntity<BookingDetail> createBookingDetail(@PathVariable String roomName,
//                                                             @RequestBody CreateBookingDetailRequest request){
//
//
//    }
}
