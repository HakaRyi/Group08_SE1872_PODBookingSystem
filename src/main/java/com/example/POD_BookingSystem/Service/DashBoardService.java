package com.example.POD_BookingSystem.Service;

import com.example.POD_BookingSystem.DTO.Response.DashBoardResponse;
import com.example.POD_BookingSystem.Entity.EBooking.Booking;
import com.example.POD_BookingSystem.Repository.ReBooking.BookingRepository;
import com.example.POD_BookingSystem.Repository.ReRoom.RoomRepository;
import com.example.POD_BookingSystem.Repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DashBoardService {
    @Autowired
    BookingRepository bookingRepository;
    @Autowired
    ServiceRepository serviceRepository;
    @Autowired
    RoomRepository roomRepository;

    public DashBoardResponse getDashBoardInformation(){
        int totalRoom = roomRepository.findAll().size();
        int activeServices = serviceRepository.findAllActiveService().size();
        List<Booking> bookingList = bookingRepository.findAll();
        int totalBooking = bookingList.size();
        long revenue = 0;
        for(Booking booking:bookingList){
            revenue+= (long) booking.getTotal();
        }

        return DashBoardResponse.builder()
                .totalRoom(totalRoom)
                .totalActiveService(activeServices)
                .totalBooking(totalBooking)
                .revenue(revenue)
                .build();
    }
}
