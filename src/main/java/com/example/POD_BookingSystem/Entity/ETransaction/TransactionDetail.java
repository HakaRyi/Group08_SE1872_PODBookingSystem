package com.example.POD_BookingSystem.Entity.ETransaction;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "transaction_detail")
public class TransactionDetail {
    @Id
    String transaction_detail_id;

    @ManyToOne
    @JoinColumn(name = "transaction_id", nullable = false, referencedColumnName = "transaction_id")
    Transaction transaction_id;

    String transaction_type;
    String description;
    double amount;
    String bank_name;
    String bank_account_number;
    String payment_status;
    LocalDateTime transaction_date;

}
