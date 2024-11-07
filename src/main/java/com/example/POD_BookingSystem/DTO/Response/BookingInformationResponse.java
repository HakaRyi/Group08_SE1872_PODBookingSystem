package com.example.POD_BookingSystem.DTO.Response;

import com.example.POD_BookingSystem.Entity.Service;
import com.example.POD_BookingSystem.Entity.Slot;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingInformationResponse {
    String status;
    double amount;
    RoomInformationResponse roomInfo;
}
