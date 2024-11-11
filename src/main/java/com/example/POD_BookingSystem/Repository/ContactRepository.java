package com.example.POD_BookingSystem.Repository;

import com.example.POD_BookingSystem.Entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepository extends JpaRepository<Contact, String> {
}
