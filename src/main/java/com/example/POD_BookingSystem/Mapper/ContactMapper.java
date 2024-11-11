package com.example.POD_BookingSystem.Mapper;

import com.example.POD_BookingSystem.DTO.Response.ContactResponse;
import com.example.POD_BookingSystem.Entity.Contact;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ContactMapper {

    ContactResponse toContactResponse(Contact contact);
}
