package com.example.POD_BookingSystem.DTO.Response;

import com.google.api.client.util.DateTime;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FeedbackResponse {
    String userName;
    String roomName;
    String description;
    double rating;
    LocalDateTime timestamp;
}
