package com.example.POD_BookingSystem.DTO.Request.Booking;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Map;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateMonthBookingRequest {
    String phone;
    String roomName;
    LocalDate startTime;
    LocalDate endTime;
    int numberOfMonth;
    Map<String, Integer> service;
}
