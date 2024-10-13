package com.example.POD_BookingSystem.Mapper.MTransaction;

import com.example.POD_BookingSystem.DTO.Request.Slot.CreateSlotRequest;
import com.example.POD_BookingSystem.DTO.Request.Slot.UpdateSlotRequest;
import com.example.POD_BookingSystem.DTO.Response.SlotResponse;
import com.example.POD_BookingSystem.Entity.Slot;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface TransactionDetailMapper {
//    Slot toSlot(CreateSlotRequest request);
//
//    SlotResponse toSlotResponse(Slot slot);
//
//    @Mapping(target = "slot_id", ignore = true)
//    void updateSlot(@MappingTarget Slot slot, UpdateSlotRequest request);
}
