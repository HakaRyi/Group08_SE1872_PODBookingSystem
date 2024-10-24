package com.example.POD_BookingSystem.Repository;

import com.example.POD_BookingSystem.Entity.FeedBack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedbackRepository extends JpaRepository<FeedBack , String> {
    @Query(value = "SELECT * FROM FeedBack WHRERE room_id = :roomId", nativeQuery = true)
    List<FeedBack> findByRoom(@Param("roomId") String roomId);
}
