package com.example.POD_BookingSystem.Entity;

import com.example.POD_BookingSystem.Entity.ERoom.Room;
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
    Boolean enable;

    @OneToMany(mappedBy = "building", cascade = CascadeType.ALL)
    private List<Room> rooms;

//    @OneToMany(mappedBy = "building", cascade = CascadeType.ALL)
//    private List<ImageBuilding> imageBuildings;
}
