package com.example.POD_BookingSystem.Entity.EBuilding;

import com.example.POD_BookingSystem.Entity.User;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;


@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Staff_Building")
public class Staff_Building {
    @Id
    String staff_building_id;

    @ManyToOne
    @JoinColumn(name = "building_id")
    Building building;

    @ManyToOne
    @JoinColumn(name = "userid_id",referencedColumnName = "userid_id")
    User user;

    LocalDate start_date;
    LocalDate end_date;

}