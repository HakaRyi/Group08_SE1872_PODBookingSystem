package com.example.POD_BookingSystem.DTO.Response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingResponse {
    String booking_id;
    String status;
    String name;
    LocalDate booking_date;
    double total;

//    public BookingResponse(com.example.POD_BookingSystem.Entity.EBooking.Booking booking) {
//        this.booking_id = booking.getBooking_id();
//        this.status = booking.getStatus();
//        this.booking_date = booking.getBooking_date();
//        this.total = booking.getTotal();
//
//        //Lấy user_id từ đối tượng User nếu không null
//        this.user_id = (booking.getUser() != null) ? booking.getUser().getUserid_id() : null;
//    }
}
