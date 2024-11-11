package com.example.POD_BookingSystem.Repository.ReBooking;

import com.example.POD_BookingSystem.Entity.EBooking.MonthBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MonthBookingRepository extends JpaRepository<MonthBooking, Integer> {
    @Query(value = "SELECT * FROM MonthBooking Where booking_id = :bookingId", nativeQuery = true)
    MonthBooking isBookingByMonth(@Param("bookingId") String bookingId);
}
