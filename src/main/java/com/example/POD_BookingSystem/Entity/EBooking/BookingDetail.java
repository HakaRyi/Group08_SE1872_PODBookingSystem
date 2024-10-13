package com.example.POD_BookingSystem.Entity.EBooking;

import com.example.POD_BookingSystem.Entity.Role;
import com.example.POD_BookingSystem.Entity.Room;
import com.example.POD_BookingSystem.Entity.Service;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;


import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "booking_detail")
public class BookingDetail {
    @Id
    String booking_detail_id;

    @ManyToOne
    @JoinColumn(name = "room_id",nullable = false, referencedColumnName = "room_id")
    Room room_id;

    @ManyToOne
    @JoinColumn(name = "booking_id",nullable = false, referencedColumnName = "booking_id") // Thiết lập khóa ngoại
    Booking booking_id;

    String booking_type;

    @ManyToOne
    @JoinColumn(name = "service_id",nullable = false, referencedColumnName = "service_id")
    Service service_id;

    int quantity;
    double total_price;
    LocalDateTime timestamp;
    String status;
    LocalDate start_time;
    LocalDate end_time;
}
