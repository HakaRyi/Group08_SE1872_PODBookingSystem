package com.example.POD_BookingSystem.DTO.Response;

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
public class BookingDetailResponse {
    String bookingId;
    Map<String, Integer> service;
    String  roomName;

    double total_price;
    LocalDate start_time;
    LocalDate end_time;
    String bookingVersion;
    List<String> slotDescription;
}
