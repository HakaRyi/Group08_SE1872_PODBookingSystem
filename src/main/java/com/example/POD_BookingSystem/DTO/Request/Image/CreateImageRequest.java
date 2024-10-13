package com.example.POD_BookingSystem.DTO.Request.Image;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateImageRequest {
    String product_name;
    String image;
    String alt_text;
}
