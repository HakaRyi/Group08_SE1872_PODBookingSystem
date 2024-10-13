package com.example.POD_BookingSystem.Repository.ReBooking;

import com.example.POD_BookingSystem.Entity.EBooking.Booking;
import com.example.POD_BookingSystem.Entity.Slot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<Booking, String> {
    @Query(value = "Select booking_id from booking order by booking_id DESC LIMIT 1;", nativeQuery = true)
    public String findLastId();

}
