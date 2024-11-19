package com.example.POD_BookingSystem.DTO.Request.OTP;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmailVerificationRequest {
    String email;
}