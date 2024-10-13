package com.example.POD_BookingSystem.Repository;

import com.example.POD_BookingSystem.Entity.Slot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SlotRepository extends JpaRepository<Slot, String> {
}
