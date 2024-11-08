package com.example.POD_BookingSystem.Controller;

import com.example.POD_BookingSystem.DTO.Request.Contact.CreateContactRequest;
import com.example.POD_BookingSystem.DTO.Request.Service.CreateServiceRequest;
import com.example.POD_BookingSystem.DTO.Request.Service.UpdateServiceRequest;
import com.example.POD_BookingSystem.DTO.Response.ApiResponse;
import com.example.POD_BookingSystem.DTO.Response.ContactResponse;
import com.example.POD_BookingSystem.DTO.Response.ServiceResponse;
import com.example.POD_BookingSystem.Service.ContactService;
import com.example.POD_BookingSystem.Service.RoomService;
import com.example.POD_BookingSystem.Service.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contacts")
public class ContactController {
    @Autowired
    ContactService contactService;

    @PostMapping("/create")
    ApiResponse<ContactResponse> createContact(@RequestBody CreateContactRequest request){
        return ApiResponse.<ContactResponse>builder()
                .data(contactService.createContact(request))
                .message("Send contact Information Successfully!!")
                .build();
    }

    @GetMapping
    ApiResponse<List<ContactResponse>> getAllContact(){
        return ApiResponse.<List<ContactResponse>>builder()
                .data(contactService.getAllContact())
                .build();
    }
}
