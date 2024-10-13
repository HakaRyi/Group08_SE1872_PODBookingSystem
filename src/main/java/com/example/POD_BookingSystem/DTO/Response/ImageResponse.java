package com.example.POD_BookingSystem.DTO.Response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImageResponse {
    String image_id;
    String product_name;
    String image;
    String alt_text;
}
