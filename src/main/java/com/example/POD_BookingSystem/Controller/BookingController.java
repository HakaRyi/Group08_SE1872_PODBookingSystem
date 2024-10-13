package com.example.POD_BookingSystem.Controller;

import com.example.POD_BookingSystem.DTO.Request.Booking.CreateBookingDetailRequest;
import com.example.POD_BookingSystem.DTO.Request.Booking.CreateBookingRequest;
import com.example.POD_BookingSystem.DTO.Response.ApiResponse;
import com.example.POD_BookingSystem.DTO.Response.BookingDetailResponse;
import com.example.POD_BookingSystem.Entity.EBooking.Booking;
import com.example.POD_BookingSystem.Repository.ReBooking.BookingRepository;
import com.example.POD_BookingSystem.Repository.ReRoom.RoomRepository;
import com.example.POD_BookingSystem.Service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class BookingController {

    @Autowired
    RoomRepository roomRepository;

    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    BookingService bookingService;


    @PostMapping("/{userName}/book")
    Booking createBooking (@PathVariable String userName){
        return bookingService.createBooking(userName);
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
