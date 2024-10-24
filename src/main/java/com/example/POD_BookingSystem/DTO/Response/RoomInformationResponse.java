package com.example.POD_BookingSystem.DTO.Response;

import com.example.POD_BookingSystem.Entity.Service;
import com.example.POD_BookingSystem.Entity.Slot;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomInformationResponse {
    String roomName;
    double amount;
    List<SlotResponse> slots;
    List<ServiceResponse> services;
    LocalDate startTime;
    LocalDate endTime;
}
