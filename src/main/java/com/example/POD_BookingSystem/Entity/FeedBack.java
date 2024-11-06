package com.example.POD_BookingSystem.Entity;

import com.example.POD_BookingSystem.Entity.EBooking.Booking;
import com.example.POD_BookingSystem.Entity.ERoom.Room;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.api.client.util.DateTime;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FeedBack {
    @Id
    String feedbackId;

    @ManyToOne
    @JoinColumn(name = "userid_id", referencedColumnName = "userid_id", nullable = false)
    User user;

    @ManyToOne
    @JoinColumn(name = "room_id", referencedColumnName = "room_id", nullable = false)
    Room room;

    String description;
    double rating;
    LocalDateTime timestamp;
}