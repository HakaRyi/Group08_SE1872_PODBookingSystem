package com.example.POD_BookingSystem.DTO.Request.Room;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateRoomRequest {
    String building_name;
    String name;
    int capacity;
    String availability;
    double price;
    String type_name;
    String description;
    LocalDate available_Date;
}
