package com.example.POD_BookingSystem.Repository.ReBooking;

import com.example.POD_BookingSystem.Entity.EBooking.Booking;
import com.example.POD_BookingSystem.Entity.EBooking.BookingDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookingDetailRepository extends JpaRepository<BookingDetail, String> {
//    @Query("Select * from booking_detail where booking_id = :booking_id")
//    List<BookingDetail> findByBooking(@Param("booking_id") String id);
    @Query(value = "Select total_price from booking_detail where room_id = :roomId and booking_type = \"ROOM\"", nativeQuery = true)
    double getRoomTotalAmount(@Param("roomId") String roomId);

    @Query(value = "Select end_time from booking_detail where room_id = :roomId and booking_type = \"ROOM\"", nativeQuery = true)
    LocalDate getRoomEndTime(@Param("roomId") String roomId);

    @Query(value = "Select start_time from booking_detail where room_id = :roomId and booking_type = \"ROOM\"", nativeQuery = true)
    LocalDate getRoomStartTime(@Param("roomId") String roomId);

    @Query(value = "SELECT * FROM booking_detail WHERE booking_id = :bookingId", nativeQuery = true)
    List<BookingDetail> findByBookingId(@Param("bookingId") String bookingId);

    @Query(value = "Select booking_detail_id from booking_detail order by booking_detail_id DESC LIMIT 1;", nativeQuery = true)
    public String findLastId();

    @Query(value = "Select bookingVersion from booking_detail WHERE booking_id =:bookingId order by bookingVersion DESC LIMIT 1;", nativeQuery = true)
    public String findLastVersion(@Param("bookingId") String bookingId);

    @Query(value = "SELECT * from booking_detail WHERE bookingVersion = :version", nativeQuery = true)
    List<BookingDetail> findDetailByVersion(@Param("version") String version);
}
