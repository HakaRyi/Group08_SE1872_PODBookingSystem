package com.example.POD_BookingSystem.Repository.RePayment;

import com.example.POD_BookingSystem.Entity.EPayment.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment,String > {
    @Query(value = "Select payment_id from Payment order by payment_id DESC LIMIT 1;", nativeQuery = true)
    public String findLastId();
    @Query(value = "Select * from Payment where booking_id like %:booking_id%", nativeQuery = true)
    public Payment findBookingId(@Param("booking_id") String bookingId);

}
