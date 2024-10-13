package com.example.POD_BookingSystem.Repository;

import com.example.POD_BookingSystem.Entity.EBuilding.Staff_Building;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StaffBuildingRepository extends JpaRepository<Staff_Building,String > {
    @Query(value = "Select staff_building_id from Staff_Building order by staff_building_id DESC LIMIT 1;", nativeQuery = true)
    public String findLastId();

    @Query(value = "SELECT * FROM Staff_Building WHERE building_id LIKE %:id%", nativeQuery = true)
    List<Staff_Building> findAllStaffBuildingbyBuilding(@Param("id") String building);
}
