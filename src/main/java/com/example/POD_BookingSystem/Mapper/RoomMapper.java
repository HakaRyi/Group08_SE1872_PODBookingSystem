package com.example.POD_BookingSystem.Mapper;

import com.example.POD_BookingSystem.DTO.Request.Room.CreateRoomRequest;
import com.example.POD_BookingSystem.DTO.Request.Room.UpdateRoomRequest;
import com.example.POD_BookingSystem.DTO.Response.RoomResponse;
import com.example.POD_BookingSystem.Entity.ERoom.Room;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface RoomMapper {
    Room toRoom(CreateRoomRequest request);

    @Mapping(source = "building.name", target = "building_name")
    @Mapping(source = "roomType.name", target = "type_name")
    RoomResponse toRoomResponse(Room room);

    @Mapping(target = "room_id", ignore = true)
    void updateRoom(@MappingTarget Room room, UpdateRoomRequest request);
}
