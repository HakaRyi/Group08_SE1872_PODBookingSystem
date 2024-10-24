package com.example.POD_BookingSystem.Mapper;

import com.example.POD_BookingSystem.DTO.Request.Slot.CreateSlotRequest;
import com.example.POD_BookingSystem.DTO.Request.Slot.UpdateSlotRequest;
import com.example.POD_BookingSystem.DTO.Response.SlotResponse;
import com.example.POD_BookingSystem.Entity.Slot;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.4 (Oracle Corporation)"
)
@Component
public class SlotMapperImpl implements SlotMapper {

    @Override
    public Slot toSlot(CreateSlotRequest request) {
        if ( request == null ) {
            return null;
        }

        Slot.SlotBuilder slot = Slot.builder();

        slot.slot_id( request.getSlot_id() );
        slot.description( request.getDescription() );

        return slot.build();
    }

    @Override
    public SlotResponse toSlotResponse(Slot slot) {
        if ( slot == null ) {
            return null;
        }

        SlotResponse.SlotResponseBuilder slotResponse = SlotResponse.builder();

        slotResponse.slot_id( slot.getSlot_id() );
        slotResponse.description( slot.getDescription() );

        return slotResponse.build();
    }

    @Override
    public void updateSlot(Slot slot, UpdateSlotRequest request) {
        if ( request == null ) {
            return;
        }

        slot.setDescription( request.getDescription() );
    }
}
