package com.example.POD_BookingSystem.Repository.ReRoom;

import com.example.POD_BookingSystem.Entity.ERoom.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, String> {
    @Query(value = "Select room_id from Room order by room_id DESC LIMIT 1;", nativeQuery = true)
    public String findLastId();

    @Query(value = "SELECT * FROM Room WHERE type_id LIKE %:id%", nativeQuery = true)
    List<Room> findAllRoomByType(@Param("id") String typeId);

    @Query(value = "SELECT * FROM Room WHERE building_id LIKE %:id%", nativeQuery = true)
    List<Room> findRoomByBuilding(@Param("id") String building);

    @Query(value = "SELECT * FROM Room WHERE availability='Available'", nativeQuery = true)
    List<Room> findRoomByStatus();

    @Query(value = "SELECT * FROM Room WHERE name LIKE %:name%", nativeQuery = true)
    List<Room> findAllRoomByName(@Param("name") String name);

    Room findByName(String name);

    @Query(value = "SELECT service_id FROM Room_service WHERE room_id = :roomId", nativeQuery = true)
    List<String> findServiceByRoom(@Param("roomId") String roomId);

    @Transactional
    @Modifying
    @Query(value = "UPDATE Room SET enable = false WHERE room_id = :roomId", nativeQuery = true)
    public void deleteRoom(@Param("roomId") String roomId);
}
