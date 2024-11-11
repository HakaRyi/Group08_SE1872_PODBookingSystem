package com.example.POD_BookingSystem.Entity;

import com.example.POD_BookingSystem.Entity.EBooking.Booking;
import com.example.POD_BookingSystem.Entity.ERoom.Room;
import com.example.POD_BookingSystem.Entity.ERoom.RoomSlot;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Slot")
public class Slot {
    @Id
    String slot_id;
    String description;

    @OneToMany(mappedBy = "slot", cascade = CascadeType.ALL)
    private List<RoomSlot> roomSlots;

    @ManyToMany
    @JoinTable(
            name = "Room_slot",
            joinColumns = @JoinColumn(name = "slot_id"),
            inverseJoinColumns = @JoinColumn(name = "booking_id")
    )
    List<Booking> bookings;
}
