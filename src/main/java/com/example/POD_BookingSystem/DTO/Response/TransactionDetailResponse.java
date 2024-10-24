package com.example.POD_BookingSystem.DTO.Response;

import com.example.POD_BookingSystem.Entity.ETransaction.Transaction;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionDetailResponse {
    String transaction_detail_id;
    String  transaction_id;
    String transaction_type;
    String description;
    double amount;
    String bank_name;
    String bank_account_number;
    String payment_status;
    LocalDateTime transaction_date;
}
