package com.example.POD_BookingSystem.Repository.ReBooking;

import com.example.POD_BookingSystem.Entity.EBooking.Booking;
import com.example.POD_BookingSystem.Entity.EBooking.BookingDetail;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookingDetailRepository extends JpaRepository<BookingDetail, String> {
    @Query(value = "SELECT total_price FROM booking_detail WHERE room_id = :roomId AND booking_type = 'ROOM' AND booking_id = :bookingId", nativeQuery = true)
    double getRoomTotalAmount(@Param("roomId") String roomId, @Param("bookingId") String bookingId);

    @Query(value = "SELECT end_time FROM booking_detail WHERE room_id = :roomId AND booking_type = 'ROOM' AND "  +
            "booking_id = :bookingId", nativeQuery = true)
    LocalDate getRoomEndTime(@Param("roomId") String roomId , @Param("bookingId") String bookingId);

    @Query(value = "SELECT start_time FROM booking_detail WHERE room_id = :roomId AND booking_type = 'ROOM' and " +
            "booking_id = :bookingId", nativeQuery = true)
    LocalDate getRoomStartTime(@Param("roomId") String roomId, @Param("bookingId") String bookingId);

    @Query(value = "SELECT * FROM booking_detail WHERE booking_id = :bookingId", nativeQuery = true)
    List<BookingDetail> findByBookingId(@Param("bookingId") String bookingId);

    @Query(value = "Select booking_detail_id from booking_detail order by booking_detail_id DESC LIMIT 1;", nativeQuery = true)
    public String findLastId();

    @Query(value = "Select bookingVersion from booking_detail WHERE booking_id =:bookingId order by bookingVersion DESC LIMIT 1;", nativeQuery = true)
    public String findLastVersion(@Param("bookingId") String bookingId);

    @Query(value = "SELECT bookingVersion FROM booking_detail WHERE room_id=:roomId order by bookingVersion DESC Limit 1;", nativeQuery = true)
    String bookingVersion(@Param("roomId") String roomId);

//    @Query(value = "SELECT * from booking_detail WHERE booking_id = :bookingId", nativeQuery = true)
//    List<BookingDetail> findDetailByVersion(@Param("version") String version);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM booking_detail WHERE booking_id = :bookingId AND status = 'PENDING'", nativeQuery = true)
    int resetBookingDetail(@Param("bookingId") String bookingId);
}
