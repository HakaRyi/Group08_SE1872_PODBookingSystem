package com.example.POD_BookingSystem.DTO.Request.FeedBack;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateFeedbackRequest {
    String description;
    double rating;
}
