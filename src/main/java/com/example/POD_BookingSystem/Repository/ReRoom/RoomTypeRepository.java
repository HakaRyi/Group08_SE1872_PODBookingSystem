package com.example.POD_BookingSystem.Repository.ReRoom;

import com.example.POD_BookingSystem.Entity.ERoom.Room_Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomTypeRepository extends JpaRepository<Room_Type, String> {
    @Query(value = "Select type_id from Room_type order by type_id DESC LIMIT 1;", nativeQuery = true)
    public String findLastId();

    Room_Type findByName(String name);
}
