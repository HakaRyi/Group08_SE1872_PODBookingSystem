package com.example.POD_BookingSystem.DTO.Request.VNPay;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RefundRequest {
    long amount;
    String TxnRef;
    LocalDate transactionDate;
}
