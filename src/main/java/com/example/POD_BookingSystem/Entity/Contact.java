package com.example.POD_BookingSystem.Entity;

import com.example.POD_BookingSystem.Entity.ERoom.Room;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Contact {
    @Id
    String contactId;
    String buildingName;
    String roomName;
    String customerName;
    String phone;
    String email;
    LocalDateTime timestamp;
}