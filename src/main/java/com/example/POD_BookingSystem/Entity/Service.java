package com.example.POD_BookingSystem.Entity;

import com.example.POD_BookingSystem.Entity.EBooking.BookingDetail;
import com.example.POD_BookingSystem.Entity.EBooking.Booking_service;
import com.example.POD_BookingSystem.Entity.ERoom.Room;
import com.example.POD_BookingSystem.Entity.ERoom.RoomService;
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
@Table(name = "Service")
public class Service {
    @Id
    String service_id;
    String name;
    double fee;
    String description;
    double price;
    Boolean enable;

    @ManyToMany
    @JoinTable(
            name = "Room_service",
            joinColumns = @JoinColumn(name = "service_id"),
            inverseJoinColumns = @JoinColumn(name = "room_id")
    )
    List<Room> rooms;

    @OneToMany(mappedBy = "service_id", cascade = CascadeType.ALL, orphanRemoval = true)
    List<BookingDetail> bookingDetails;

    @OneToMany(mappedBy = "service")
    private List<Booking_service> bookingServices;

    @OneToMany(mappedBy = "service")
    private List<RoomService> serviceRooms;
}
