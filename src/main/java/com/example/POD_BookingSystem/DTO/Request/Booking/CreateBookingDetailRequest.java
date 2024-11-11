package com.example.POD_BookingSystem.DTO.Request.Booking;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateBookingDetailRequest {
    String roomName;
    Map<String, Integer> service;
    LocalDate start_time;
    LocalDate end_time;
    Map<String, List<String>> slots;
}
