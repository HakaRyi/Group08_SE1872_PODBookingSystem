package com.example.POD_BookingSystem.Entity.EBooking;

import com.example.POD_BookingSystem.Entity.Service;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "booking_service")
public class Booking_service {
    @EmbeddedId
    private BookingServiceId id;
    String status;
    int quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("booking_id")  // Ánh xạ booking_id từ EmbeddedId
    @JoinColumn(name = "booking_id", insertable = false, updatable = false)
    private Booking booking;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("service_id")  // Ánh xạ service_id từ EmbeddedId
    @JoinColumn(name = "service_id", insertable = false, updatable = false)
    private Service service;

}
