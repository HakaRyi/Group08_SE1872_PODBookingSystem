package com.example.POD_BookingSystem.Mapper;

import com.example.POD_BookingSystem.DTO.Request.Building.UpdateBuildingRequest;
import com.example.POD_BookingSystem.DTO.Response.RoomTypeResponse;
import com.example.POD_BookingSystem.Entity.ERoom.Room_Type;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.4 (Oracle Corporation)"
)
@Component
public class RoomTypeMapperImpl implements RoomTypeMapper {

    @Override
    public RoomTypeResponse toRoomTypeResponse(Room_Type roomType) {
        if ( roomType == null ) {
            return null;
        }

        RoomTypeResponse.RoomTypeResponseBuilder roomTypeResponse = RoomTypeResponse.builder();

        roomTypeResponse.type_id( roomType.getType_id() );
        roomTypeResponse.name( roomType.getName() );

        return roomTypeResponse.build();
    }

    @Override
    public void updateRoom(Room_Type room_type, UpdateBuildingRequest request) {
        if ( request == null ) {
            return;
        }

        room_type.setName( request.getName() );
    }
}
