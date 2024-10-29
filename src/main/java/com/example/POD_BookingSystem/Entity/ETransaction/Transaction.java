package com.example.POD_BookingSystem.Entity.ETransaction;

import com.example.POD_BookingSystem.Entity.EBooking.Booking;
import com.example.POD_BookingSystem.Entity.Role;
import com.example.POD_BookingSystem.Entity.User;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Transaction")
public class Transaction {
    @Id
    String transaction_id;

    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false, referencedColumnName = "userid_id") // Thiết lập khóa ngoại
    User user_id;

    @ManyToOne
    @JoinColumn(name = "booking_id",nullable = false, referencedColumnName = "booking_id") // Thiết lập khóa ngoại
    Booking booking_id;

    @OneToMany(mappedBy = "transaction_id", cascade = CascadeType.ALL, orphanRemoval = true)
    List<TransactionDetail> transactionDetails;
}
