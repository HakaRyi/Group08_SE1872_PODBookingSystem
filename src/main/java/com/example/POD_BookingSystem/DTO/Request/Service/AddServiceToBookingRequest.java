package com.example.POD_BookingSystem.DTO.Request.Service;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Map;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddServiceToBookingRequest {
    Map<String, Integer> services;
}
