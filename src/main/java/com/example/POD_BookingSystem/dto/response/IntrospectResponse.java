package com.example.POD_BookingSystem.DTO.Response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class IntrospectResponse {
    boolean valid;
}