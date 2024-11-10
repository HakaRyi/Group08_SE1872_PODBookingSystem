package com.example.POD_BookingSystem.DTO.Response;

import com.example.POD_BookingSystem.Entity.User;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MonthBookingResponse {
    String booking_id;
    LocalDate booking_date;
    String total;
    String status;
    LocalDate startTime;
    LocalDate endTime;
    String userName;
    String phoneNumber;
}