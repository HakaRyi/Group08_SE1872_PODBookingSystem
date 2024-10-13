package com.example.POD_BookingSystem.DTO.Request.Image;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateImageRequest {
    String product_id;
    String image;
    String alt_text;
}
