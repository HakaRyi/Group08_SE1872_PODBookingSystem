package com.example.POD_BookingSystem.Repository.ReRoom;

import com.example.POD_BookingSystem.Entity.EBuilding.Building;
import com.example.POD_BookingSystem.Entity.ERoom.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, String> {
    @Query(value = "Select room_id from Room order by room_id DESC LIMIT 1;", nativeQuery = true)
    public String findLastId();

    @Query(value = "SELECT * FROM Room WHERE type_id LIKE %:id%", nativeQuery = true)
    List<Building> findAllRoomByType(@Param("id") String typeId);

    @Query(value = "SELECT * FROM Room WHERE room_name LIKE %:name%", nativeQuery = true)
    List<Room> findAllRoomByName(@Param("name") String name);

    Room findByName(String name);

    @Query(value = "SELECT service_id FROM Room_service WHERE room_id = :roomId", nativeQuery = true)
    List<String> findServiceByRoom(@Param("roomId") String roomId);
}
