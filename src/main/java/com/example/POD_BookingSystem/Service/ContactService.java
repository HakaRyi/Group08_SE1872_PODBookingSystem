package com.example.POD_BookingSystem.Service;

import com.example.POD_BookingSystem.DTO.Request.Contact.CreateContactRequest;
import com.example.POD_BookingSystem.DTO.Response.ContactResponse;
import com.example.POD_BookingSystem.Entity.Contact;
import com.example.POD_BookingSystem.Entity.ERoom.Room;
import com.example.POD_BookingSystem.Mapper.ContactMapper;
import com.example.POD_BookingSystem.Repository.ContactRepository;
import com.example.POD_BookingSystem.Repository.ReRoom.RoomRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
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
