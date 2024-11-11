package com.example.POD_BookingSystem.DTO.Request.Booking;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateBookingRequest {
    LocalDate booking_date;
    String user_id;
    String room_id;
    String booking_type;
    String service_id;
    int quantity;
}
