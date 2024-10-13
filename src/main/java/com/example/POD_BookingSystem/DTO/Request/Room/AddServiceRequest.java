package com.example.POD_BookingSystem.DTO.Request.Room;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddServiceRequest {
    String service_name;
    int quantity;
}
