package com.example.POD_BookingSystem.Repository;

import com.example.POD_BookingSystem.Entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<Image,String> {
    @Query(value = "Select image_id from Image order by image_id DESC LIMIT 1;", nativeQuery = true)
    public String findLastId();
}
