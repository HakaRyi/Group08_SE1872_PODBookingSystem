package com.example.POD_BookingSystem.DTO.Request.Booking;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CancelPaymentRequest {
    int amount;
}
