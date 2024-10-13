package com.example.POD_BookingSystem.Service;

import com.example.POD_BookingSystem.DTO.Request.Slot.CreateSlotRequest;
import com.example.POD_BookingSystem.DTO.Request.Slot.UpdateSlotRequest;
import com.example.POD_BookingSystem.DTO.Response.SlotResponse;
import com.example.POD_BookingSystem.Entity.Slot;
import com.example.POD_BookingSystem.Exception.AppException;
import com.example.POD_BookingSystem.Exception.ErrorCode;
import com.example.POD_BookingSystem.Mapper.SlotMapper;
import com.example.POD_BookingSystem.Repository.ReBooking.BookingDetailRepository;
import com.example.POD_BookingSystem.Repository.ReBooking.BookingRepository;
import com.example.POD_BookingSystem.Repository.SlotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingService {
    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    BookingDetailRepository bookingDetailRepository;


}
