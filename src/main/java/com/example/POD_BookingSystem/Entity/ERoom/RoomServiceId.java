package com.example.POD_BookingSystem.Entity.ERoom;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
@Builder

public class RoomServiceId implements Serializable {
    private String roomId;
    private String serviceId;
}