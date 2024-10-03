package com.example.POD_BookingSystem.DTO.Request.Building;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateBuildingRequest {
    String name;
    String address;
    String description;
    String location;
}