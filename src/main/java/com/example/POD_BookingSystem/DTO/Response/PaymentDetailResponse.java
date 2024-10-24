package com.example.POD_BookingSystem.DTO.Response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentDetailResponse {
    String payment_detail_id;
    String payment_id;
    String item_type;
    String room_id;
    String service_id;
    String item_description;
    String quantity;
    double price;
    String payment_status;
    LocalDateTime payment_date;
    String payment_method;
}
