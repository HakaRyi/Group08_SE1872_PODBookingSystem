package com.example.POD_BookingSystem.Entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Refund {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String TxnRef;
    double amount;
    LocalDate bookingDate;
    String bookingId;
}
