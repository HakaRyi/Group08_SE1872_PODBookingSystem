package com.example.POD_BookingSystem.Repository.ReBooking;

import com.example.POD_BookingSystem.Entity.EBooking.Booking;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<Booking, String> {
    @Query(value = "Select booking_id from booking order by booking_id DESC LIMIT 1;", nativeQuery = true)
    public String findLastId();

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM Booking_bookedService WHERE Booking_booking_id = :bookingId", nativeQuery = true)
    public void resetBookingService(@Param("bookingId") String bookingId);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM Booking_bookingDate WHERE Booking_booking_id = :bookingId", nativeQuery = true)
    public void resetBookingDate(@Param("bookingId") String bookingId);
}
