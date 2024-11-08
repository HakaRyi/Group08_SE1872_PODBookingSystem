package com.example.POD_BookingSystem.Repository;

import com.example.POD_BookingSystem.Entity.Contact;
import com.example.POD_BookingSystem.Entity.EBuilding.Building;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactRepository extends JpaRepository<Contact, String> {
}
