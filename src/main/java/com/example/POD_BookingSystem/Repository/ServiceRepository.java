package com.example.POD_BookingSystem.Repository;

import com.example.POD_BookingSystem.Entity.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceRepository extends JpaRepository<Service, String> {
    @Query(value = "Select service_id from Service order by service_id DESC LIMIT 1;", nativeQuery = true)
    public String findLastId();

    @Query(value = "SELECT * FROM Service WHERE name LIKE %:name%", nativeQuery = true)
    List<Service> findAllServiceByName(@Param("name") String name);

    Service findByName(String name);

    boolean existsByName(String name);

    @Query(value = "SELECT DISTINCT bs.service_id\n" +
            "FROM booking_detail bd\n" +
            "JOIN Room r ON bd.room_id = r.room_id\n" +
            "JOIN booking b ON bd.booking_id = b.booking_id\n" +
            "JOIN booking_service bs ON b.booking_id = bs.booking_id\n" +
            "WHERE b.booking_id = :bookingId and r.room_id = :roomId;", nativeQuery = true)
    List<String> getServiceByRoom(@Param("bookingId") String bookingId, @Param("roomId") String roomId);
}
