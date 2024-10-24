package com.example.POD_BookingSystem.Repository.ReRoom;

import com.example.POD_BookingSystem.Entity.ERoom.RoomSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomSlotRepository extends JpaRepository<RoomSlot, String> {
    @Query(value = "Select uId from Room_slot order by uId DESC LIMIT 1;", nativeQuery = true)
    public String findLastId();

    @Query(value = "SELECT * FROM Room_slot WHERE slot_id = :slotId and room_id = :roomId", nativeQuery = true)
    RoomSlot findRoomSlotByRoomAndSlot(@Param("slotId") String slotId, @Param("roomId") String roomId);

}
