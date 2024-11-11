package com.example.POD_BookingSystem.Repository.ReBooking;

import com.example.POD_BookingSystem.Entity.EBooking.Booking_service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingServiceRepository extends JpaRepository<Booking_service, String> {
    @Query(value = "Select id from booking_service order by id DESC LIMIT 1;", nativeQuery = true)
    public String findLastId();
    @Query(value = "SELECT * FROM booking_service WHERE service_id = :serviceId AND booking_id = :bookingId", nativeQuery = true)
    Booking_service findExistService(@Param("serviceId") String serviceId, @Param("bookingId") String bookingId);

}
