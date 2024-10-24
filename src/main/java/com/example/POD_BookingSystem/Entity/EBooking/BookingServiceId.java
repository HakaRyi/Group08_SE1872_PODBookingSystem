package com.example.POD_BookingSystem.Entity.EBooking;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable
public class BookingServiceId implements Serializable {
    private String booking_id;
    private String service_id;
}