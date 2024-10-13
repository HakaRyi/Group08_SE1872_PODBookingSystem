package com.example.POD_BookingSystem.Mapper.MBooking;

import com.example.POD_BookingSystem.DTO.Request.Slot.CreateSlotRequest;
import com.example.POD_BookingSystem.DTO.Request.Slot.UpdateSlotRequest;
import com.example.POD_BookingSystem.DTO.Response.BookingDetailResponse;
import com.example.POD_BookingSystem.DTO.Response.SlotResponse;
import com.example.POD_BookingSystem.Entity.EBooking.BookingDetail;
import com.example.POD_BookingSystem.Entity.Slot;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface BookingDetailMapper {
    BookingDetailResponse toBookingDetailResponse(BookingDetail bookingDetail);

    @Mapping(target = "slot_id", ignore = true)
    void updateSlot(@MappingTarget Slot slot, UpdateSlotRequest request);
}
