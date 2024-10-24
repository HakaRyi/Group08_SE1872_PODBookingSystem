package com.example.POD_BookingSystem.Repository;

import com.example.POD_BookingSystem.Entity.Building;
import com.example.POD_BookingSystem.Entity.Slot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SlotRepository extends JpaRepository<Slot, String> {
    @Query(value = "SELECT s.slot_id\n" +
            "FROM Room_slot rs\n" +
            "JOIN Room r ON rs.room_id = r.room_id\n" +
            "JOIN Slot s ON rs.slot_id = s.slot_id\n" +
            "WHERE rs.booking_id = :bookingId and r.room_id = :roomId;", nativeQuery = true)
    List<String> getSlotsInRoom(@Param("bookingId") String bookingId, @Param("roomId") String roomId);
}
