package com.example.POD_BookingSystem.DTO.Request.VNPay;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VNPayRequest {
    long amount;
    String bankCode;
}
