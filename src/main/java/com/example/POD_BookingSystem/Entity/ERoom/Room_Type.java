package com.example.POD_BookingSystem.Entity.ERoom;

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
@Table(name = "Room_type")
public class Room_Type {
    @Id
    String type_id;
    @Column(name = "name")
    String name;

    @OneToMany(mappedBy = "roomType", cascade = CascadeType.ALL)
    List<Room> rooms;
}
