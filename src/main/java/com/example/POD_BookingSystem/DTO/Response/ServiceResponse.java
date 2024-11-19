package com.example.POD_BookingSystem.DTO.Response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServiceResponse {
    String service_id;
    String name;
    String description;
    double fee;
    double price;
    Boolean enable;
}
