 package com.example.POD_BookingSystem.Entity.EBooking;

import com.example.POD_BookingSystem.Entity.User;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "MonthBooking")
public class MonthBooking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int month_booking_id;

    String booking_id;
    LocalDate booking_date;
    double total;
    String status;
    LocalDate startTime;
    LocalDate endTime;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "userid_id")
    User user;
}
