package com.example.POD_BookingSystem.Repository;

import com.example.POD_BookingSystem.Entity.ERoom.RoomService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomServiceRepository extends JpaRepository<RoomService, String> {
    @Query(value = "Select id from Room_service order by id DESC LIMIT 1;", nativeQuery = true)
    public String findLastId();

    @Query(value = "SELECT * FROM Room_service WHERE room_id = :roomId and service_id = :serviceId", nativeQuery = true)
    RoomService findByRoomAndService(@Param("roomId") String roomId, @Param("serviceId") String serviceId);

}
