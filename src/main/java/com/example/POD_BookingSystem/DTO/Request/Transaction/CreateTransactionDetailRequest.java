package com.example.POD_BookingSystem.DTO.Request.Transaction;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateTransactionDetailRequest {
    String username;
    // bổ sung nếu cần thiết
}