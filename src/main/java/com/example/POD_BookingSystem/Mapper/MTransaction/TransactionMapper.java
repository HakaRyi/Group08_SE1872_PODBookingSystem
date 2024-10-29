package com.example.POD_BookingSystem.Mapper.MTransaction;

import com.example.POD_BookingSystem.DTO.Request.Slot.UpdateSlotRequest;
import com.example.POD_BookingSystem.DTO.Response.BookingResponse;
import com.example.POD_BookingSystem.Entity.Slot;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

//    BookingResponse toSlotResponse(Slot slot);
//
//    @Mapping(target = "slot_id", ignore = true)
//    void updateSlot(@MappingTarget Slot slot, UpdateSlotRequest request);
}
