package com.example.POD_BookingSystem.Entity.ERoom;

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
@Table(name = "Room_service")
public class RoomService {
    @EmbeddedId
    RoomServiceId id;

    @ManyToOne
    @MapsId("roomId")
    @JoinColumn(name = "room_id")
    Room room;

    @ManyToOne
    @MapsId("serviceId")
    @JoinColumn(name = "service_id")
    Service service;

    private int quantity;
}
