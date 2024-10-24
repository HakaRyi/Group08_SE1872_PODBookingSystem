package com.example.POD_BookingSystem.Repository;

import com.example.POD_BookingSystem.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,String> {

    boolean existsByUsername(String username);
    boolean existsByPhone(String phone);
    boolean existsByEmail(String email);
    Optional<User> findByUsername(String username);

    Optional<User> findByPhone(String phone);
    @Query(value = "Select * from User where name like %:name% and role_id='Role-03'", nativeQuery = true)
    public User findName(@Param("name") String name);
    @Query(value = "Select * from User where username like %:username%", nativeQuery = true)
    public User findUsername(@Param("username") String name);
    @Query(value = "Select * from User where userid_id like %:userid_id%", nativeQuery = true)
    public User findUserID(@Param("userid_id") String name);
    @Query(value = "Select userid_id from User order by userid_id DESC LIMIT 1;", nativeQuery = true)
    public String findLastId();

}