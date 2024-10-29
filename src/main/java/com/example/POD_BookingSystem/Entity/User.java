package com.example.POD_BookingSystem.Entity;

import com.example.POD_BookingSystem.Entity.EBooking.Booking;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name ="User")
public class User {
    @Id
    String userid_id;
    String name;
    String username;
    String password;
    String phone;
    String email;
    @ManyToOne
    @JoinColumn(name = "role_id",nullable = false, referencedColumnName = "role_id") // Thiết lập khóa ngoại
    Role role;

    @OneToMany(mappedBy = "user")
    private List<FeedBack> feedbacks;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    List<Booking> bookings;

    String VIP;
}