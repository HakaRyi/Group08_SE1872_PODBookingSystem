package com.example.POD_BookingSystem.Entity;

import com.example.POD_BookingSystem.Entity.EBooking.Booking;
import com.example.POD_BookingSystem.Entity.ETransaction.Transaction;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name ="User")
public class User {
    @Id
    String userid_id;
    String name;
    String username;
    String password;
    String phone;
    String email;
   // @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "role_id",nullable = false, referencedColumnName = "role_id") // Thiết lập khóa ngoại
    Role role_id;

    String VIP;

    @OneToMany(mappedBy = "user_id", cascade = CascadeType.ALL)
    List<Transaction> transaction;

    // Quan hệ OneToMany với Booking
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    List<Booking> bookings;
}