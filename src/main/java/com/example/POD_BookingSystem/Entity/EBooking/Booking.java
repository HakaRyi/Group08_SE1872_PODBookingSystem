package com.example.POD_BookingSystem.Entity.EBooking;

import com.example.POD_BookingSystem.Entity.ERoom.RoomSlot;
import com.example.POD_BookingSystem.Entity.FeedBack;
import com.example.POD_BookingSystem.Entity.Slot;
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
    String user_id;
    LocalDate booking_date;
    double total;
    String status;

    @ElementCollection
    Map<String, Integer> bookedService;

    @ElementCollection
    Map<String, List<LocalDate>> bookingDate;

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
