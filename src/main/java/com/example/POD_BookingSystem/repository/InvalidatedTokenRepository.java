package com.example.POD_BookingSystem.Repository;

import com.example.POD_BookingSystem.Entity.InvalidatedToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvalidatedTokenRepository extends JpaRepository<InvalidatedToken,String> {

}