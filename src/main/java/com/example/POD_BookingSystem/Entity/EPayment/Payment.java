package com.example.POD_BookingSystem.Entity.EPayment;

import com.example.POD_BookingSystem.Entity.EBooking.Booking;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Payment")
public class Payment {
    @Id
    String payment_id;
    double total_amount;
    @ManyToOne
    @JoinColumn(name = "booking_id",nullable = false, referencedColumnName = "booking_id") // Thiết lập khóa ngoại
    Booking booking_id;



}
