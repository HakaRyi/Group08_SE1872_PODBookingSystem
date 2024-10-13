package com.example.POD_BookingSystem.Entity.EBooking;

import com.example.POD_BookingSystem.Entity.ETransaction.Transaction;
import com.example.POD_BookingSystem.Entity.Slot;
import com.example.POD_BookingSystem.Entity.User;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.List;

@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "booking")
public class Booking {
    @Id

    String booking_id;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, referencedColumnName = "userid_id")
    User user_id;
    LocalDate booking_date;
    double total;

    @OneToMany(mappedBy = "booking_id", cascade = CascadeType.ALL, orphanRemoval = true)
    List<BookingDetail> bookingDetails;
    @OneToMany(mappedBy = "booking_id", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Transaction> transaction;

    @ManyToMany
    @JoinTable(
            name = "Room_slot",
            joinColumns = @JoinColumn(name = "booking_id"),
            inverseJoinColumns = @JoinColumn(name = "slot_id")
    )
    List<Slot> slots;
}
