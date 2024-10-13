package com.example.POD_BookingSystem.Entity;

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
@Table(name = "Building")
public class Building {
    @Id
    String building_id;
    String name;
    String address;
    String description;
    String location;

    @OneToMany(mappedBy = "building", cascade = CascadeType.ALL)
    private List<Room> rooms;

    @OneToMany(mappedBy = "building", cascade = CascadeType.ALL)
    private List<ImageBuilding> imageBuildings;
}
