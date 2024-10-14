package com.example.POD_BookingSystem.DTO.Request.Room;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Map;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddServiceRequest {
    Map<String, Integer> services;
}
