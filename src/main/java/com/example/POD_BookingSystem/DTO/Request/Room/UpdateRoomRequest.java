package com.example.POD_BookingSystem.DTO.Request.Room;

import jakarta.persistence.Column;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateRoomRequest {
    String name;
    int capacity;
    String availability;
    double price;
    String description;
    LocalDate available_Date;
}
