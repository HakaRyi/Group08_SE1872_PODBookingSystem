package com.example.POD_BookingSystem.Service;

import com.example.POD_BookingSystem.DTO.Request.Booking.ConfirmRequest;
import com.example.POD_BookingSystem.DTO.Request.Booking.CreateBookingDetailRequest;
import com.example.POD_BookingSystem.DTO.Request.Contact.CreateContactRequest;
import com.example.POD_BookingSystem.DTO.Request.Service.AddServiceToBookingRequest;
import com.example.POD_BookingSystem.DTO.Response.*;
import com.example.POD_BookingSystem.Entity.Contact;
import com.example.POD_BookingSystem.Entity.EBooking.Booking;
import com.example.POD_BookingSystem.Entity.EBooking.BookingDetail;
import com.example.POD_BookingSystem.Entity.EBooking.BookingServiceId;
import com.example.POD_BookingSystem.Entity.EBooking.Booking_service;
import com.example.POD_BookingSystem.Entity.ERoom.Room;
import com.example.POD_BookingSystem.Entity.ERoom.RoomService;
import com.example.POD_BookingSystem.Entity.ERoom.RoomSlot;
import com.example.POD_BookingSystem.Entity.Slot;
import com.example.POD_BookingSystem.Entity.User;
import com.example.POD_BookingSystem.Exception.AppException;
import com.example.POD_BookingSystem.Exception.ErrorCode;
import com.example.POD_BookingSystem.Mapper.ContactMapper;
import com.example.POD_BookingSystem.Mapper.MBooking.BookingMapper;
import com.example.POD_BookingSystem.Mapper.ServiceMapper;
import com.example.POD_BookingSystem.Mapper.SlotMapper;
import com.example.POD_BookingSystem.Repository.ContactRepository;
import com.example.POD_BookingSystem.Repository.ReBooking.BookingDetailRepository;
import com.example.POD_BookingSystem.Repository.ReBooking.BookingRepository;
import com.example.POD_BookingSystem.Repository.ReBooking.BookingServiceRepository;
import com.example.POD_BookingSystem.Repository.ReRoom.RoomRepository;
import com.example.POD_BookingSystem.Repository.ReRoom.RoomSlotRepository;
import com.example.POD_BookingSystem.Repository.ServiceRepository;
import com.example.POD_BookingSystem.Repository.SlotRepository;
import com.example.POD_BookingSystem.Repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ContactService {
    @Autowired
    ContactRepository contactRepository;

    @Autowired
    RoomRepository roomRepository;

    @Autowired
    ContactMapper contactMapper;

    private final SimpMessagingTemplate messagingTemplate;

    public ContactService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    //Create Contact Information
    public ContactResponse createContact(CreateContactRequest request) {
        Room room = roomRepository.findByName(request.getRoomName());
        if(room!=null) {


            Contact contact = Contact.builder()
                    .contactId(GenerateContactId())
                    .buildingName(room.getBuilding().getName())
                    .phone(request.getPhone())
                    .customerName(request.getCustomerName())
                    .email(request.getEmail())
                    .roomName(request.getRoomName())
                    .timestamp(LocalDateTime.now())
                    .build();
            contactRepository.save(contact);

            messagingTemplate.convertAndSend("/topic/contact-updates", "New contact created");
            ContactResponse response = contactMapper.toContactResponse(contact);
            response.setBuildingName(room.getBuilding().getName());
            return contactMapper.toContactResponse(contact);
        }
        return null;
    }

    public List<ContactResponse> getAllContact() {
        return contactRepository.findAll()
                .stream()
                .map(contactMapper::toContactResponse)
                .collect(Collectors.toList());
    }

    private String GenerateContactId() {
        int number = contactRepository.findAll().size() + 1;
        String id = "CT-" + number;
        return id;
    }


//    const socket = new SockJS('/ws');
//const stompClient = Stomp.over(socket);
//
//stompClient.connect({}, () => {
//        stompClient.subscribe('/topic/contact-updates', (message) => {
//        if (message.body === "New contact created") {
//            // Gọi API để lấy danh sách contact mới nhất
//            fetch('/api/contacts')
//                    .then(response => response.json())
//                .then(data => {
//                    updateContactListOnUI(data.data); // Hàm cập nhật UI của bạn
//                });
//        }
//    });
//    });

}
