package com.example.POD_BookingSystem.DTO.Request.Contact;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateContactRequest {
    String roomName;
    String customerName;
    String phone;
    String email;
}
