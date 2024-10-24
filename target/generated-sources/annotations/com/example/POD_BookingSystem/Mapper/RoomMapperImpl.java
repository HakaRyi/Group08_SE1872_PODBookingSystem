package com.example.POD_BookingSystem.Mapper;

import com.example.POD_BookingSystem.DTO.Request.Room.CreateRoomRequest;
import com.example.POD_BookingSystem.DTO.Request.Room.UpdateRoomRequest;
import com.example.POD_BookingSystem.DTO.Response.RoomResponse;
import com.example.POD_BookingSystem.Entity.Building;
import com.example.POD_BookingSystem.Entity.ERoom.Room;
import com.example.POD_BookingSystem.Entity.ERoom.Room_Type;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.4 (Oracle Corporation)"
)
@Component
public class RoomMapperImpl implements RoomMapper {

    @Override
    public Room toRoom(CreateRoomRequest request) {
        if ( request == null ) {
            return null;
        }

        Room.RoomBuilder room = Room.builder();

        room.capacity( request.getCapacity() );
        room.availability( request.getAvailability() );
        room.price( request.getPrice() );
        room.description( request.getDescription() );
        room.available_Date( request.getAvailable_Date() );

        return room.build();
    }

    @Override
    public RoomResponse toRoomResponse(Room room) {
        if ( room == null ) {
            return null;
        }

        RoomResponse.RoomResponseBuilder roomResponse = RoomResponse.builder();

        roomResponse.building_id( roomBuildingBuilding_id( room ) );
        roomResponse.type_name( roomRoomTypeName( room ) );
        roomResponse.room_id( room.getRoom_id() );
        roomResponse.name( room.getName() );
        roomResponse.capacity( room.getCapacity() );
        roomResponse.availability( room.getAvailability() );
        roomResponse.price( room.getPrice() );
        roomResponse.description( room.getDescription() );
        roomResponse.available_Date( room.getAvailable_Date() );

        return roomResponse.build();
    }

    @Override
    public void updateRoom(Room room, UpdateRoomRequest request) {
        if ( request == null ) {
            return;
        }

        room.setName( request.getName() );
        room.setCapacity( request.getCapacity() );
        room.setAvailability( request.getAvailability() );
        room.setPrice( request.getPrice() );
        room.setDescription( request.getDescription() );
        room.setAvailable_Date( request.getAvailable_Date() );
    }

    private String roomBuildingBuilding_id(Room room) {
        if ( room == null ) {
            return null;
        }
        Building building = room.getBuilding();
        if ( building == null ) {
            return null;
        }
        String building_id = building.getBuilding_id();
        if ( building_id == null ) {
            return null;
        }
        return building_id;
    }

    private String roomRoomTypeName(Room room) {
        if ( room == null ) {
            return null;
        }
        Room_Type roomType = room.getRoomType();
        if ( roomType == null ) {
            return null;
        }
        String name = roomType.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }
}
