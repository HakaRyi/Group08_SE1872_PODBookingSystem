package com.example.POD_BookingSystem.Repository.ReTransaction;

import com.example.POD_BookingSystem.Entity.EBooking.Booking;
import com.example.POD_BookingSystem.Entity.ETransaction.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction,String > {
    @Query(value = "Select transaction_id from Transaction order by transaction_id DESC LIMIT 1;", nativeQuery = true)
    public String findLastId();
    @Query(value = "Select * from Transaction where booking_id like %:booking_id%", nativeQuery = true)
    public Transaction findBookingId(@Param("booking_id") String bookingId);

}
