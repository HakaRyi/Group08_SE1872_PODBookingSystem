package com.example.POD_BookingSystem.Entity.EPayment;

import com.example.POD_BookingSystem.Entity.ERoom.Room;
import com.example.POD_BookingSystem.Entity.ETransaction.Transaction;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "payment_detail")
public class PaymentDetail {
    @Id
    String payment_detail_id;

    @ManyToOne(fetch = FetchType.LAZY,  cascade = CascadeType.PERSIST)
    @JoinColumn(name = "payment_id", nullable = false, referencedColumnName = "payment_id")
    Payment payment_id;

    String item_type;

    @ManyToOne(fetch = FetchType.LAZY,  cascade = CascadeType.PERSIST)
    @JoinColumn(name = "room_id", nullable = false)
    Room room;

    String service_id;
    String item_description;
    int quantity;
    double price;
    String payment_status;
    LocalDateTime payment_date;
    String payment_method;


}
