package com.example.POD_BookingSystem.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BuildingResponse {
    String building_id;
    String name;
    String address;
    String description;
    String location;
}
