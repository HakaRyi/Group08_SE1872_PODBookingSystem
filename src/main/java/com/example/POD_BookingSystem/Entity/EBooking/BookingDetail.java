package com.example.POD_BookingSystem.Entity.EBooking;

import com.example.POD_BookingSystem.Entity.ERoom.Room;
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
    String booking_type;
    String service_id;
    int quantity;
    double total_price;
    LocalDateTime timestamp;
    String status;
    LocalDate start_time;
    LocalDate end_time;
    String bookingVersion;

    @ManyToOne(fetch = FetchType.LAZY,  cascade = CascadeType.PERSIST)
    @JoinColumn(name = "room_id", nullable = false) // Liên kết với Room
    private Room room;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booking_id")
    private Booking booking;
}
