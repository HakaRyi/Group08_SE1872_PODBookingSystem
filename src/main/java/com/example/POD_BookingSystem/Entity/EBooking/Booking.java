package com.example.POD_BookingSystem.Entity.EBooking;

import com.example.POD_BookingSystem.Entity.ERoom.RoomSlot;
import com.example.POD_BookingSystem.Entity.Slot;
import com.example.POD_BookingSystem.Entity.User;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "booking")
public class Booking {
    @Id
    String booking_id;

    @ManyToOne
    @JoinColumn(name = "user_id",referencedColumnName = "userid_id")
    User user;

    LocalDate booking_date;
    double total;
    String status;
    String TxnRef;

    @ElementCollection
    Map<String, Integer> bookedService;

    @ElementCollection
    Map<String, List<String>> bookingDate;

    @ManyToMany
    @JoinTable(
            name = "Room_slot",
            joinColumns = @JoinColumn(name = "booking_id"),  // khóa chính của Booking
            inverseJoinColumns = @JoinColumn(name = "slot_id")  // khóa chính của Slot
    )
    List<Slot> slots;

    @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL)
    private List<BookingDetail> bookingDetails;

    @OneToMany(mappedBy = "booking")
    private List<Booking_service> bookingServices;

    @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL)
    private List<RoomSlot> roomSlots;

}
