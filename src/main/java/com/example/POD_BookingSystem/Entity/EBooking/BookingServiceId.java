package com.example.POD_BookingSystem.Entity.EBooking;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class BookingServiceId implements Serializable {
    private String booking_id;
    private String service_id;
}