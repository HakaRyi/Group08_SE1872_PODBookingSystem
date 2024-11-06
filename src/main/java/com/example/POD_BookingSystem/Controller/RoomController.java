package com.example.POD_BookingSystem.Controller;

import com.example.POD_BookingSystem.DTO.Request.Room.AddServiceRequest;
import com.example.POD_BookingSystem.DTO.Request.Room.CreateRoomRequest;
import com.example.POD_BookingSystem.DTO.Request.Room.UpdateRoomRequest;
import com.example.POD_BookingSystem.DTO.Response.ApiResponse;
import com.example.POD_BookingSystem.DTO.Response.RoomResponse;
import com.example.POD_BookingSystem.DTO.Response.ServiceResponse;
import com.example.POD_BookingSystem.Entity.Service;
import com.example.POD_BookingSystem.Service.RoomService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/rooms")
public class RoomController {
    @Autowired
    RoomService roomService;

    //Create Room API
    @PostMapping
    ApiResponse<RoomResponse> createRoom(@RequestBody CreateRoomRequest request){
        return ApiResponse.<RoomResponse>builder()
                .data(roomService.createRoom(request))
                .build();
    }

    //Get All Room API
    @GetMapping
    ApiResponse<List<RoomResponse>> getRooms(){
        return ApiResponse.<List<RoomResponse>>builder()
                .data(roomService.getAllRooms())
                .build();
    }

    //Get Room By Name API
    @GetMapping("/{name}")
    ApiResponse<List<RoomResponse>> getRoom(@PathVariable String name){
        return ApiResponse.<List<RoomResponse>>builder()
                .data(roomService.getRoom(name))
                .build();
    }

    //Update Room API
    @PutMapping("/{id}")
    ApiResponse<RoomResponse> updateRoom(@PathVariable String id, @RequestBody UpdateRoomRequest request) {
        return ApiResponse.<RoomResponse>builder().data(roomService.updateRoom(id, request)).build();
    }

    //Delete Room API
    @DeleteMapping("/{id}")
    ApiResponse<Void> deleteRoom(@PathVariable String id) {
        roomService.deleteRoom(id);
        return ApiResponse.<Void>builder().message("Delete Successfully !!!").build();
    }

    @PostMapping("/{id}/service/add")
    ApiResponse<Void> addServiceToRoom(@RequestBody AddServiceRequest request, @PathVariable String id){
        roomService.addService(request, id);
        return ApiResponse.<Void>builder()
                .message("Add new service Successfully !!!")
                .build();
    }

    @GetMapping("/{id}/service/get")
    ApiResponse<List<ServiceResponse>> getServiceInRoom(@PathVariable String id){
        return ApiResponse.<List<ServiceResponse>>builder()
                .data(roomService.getServicesInRoom(id))
                .build();
    }

    @GetMapping("/building/{id}")
    ApiResponse<List<RoomResponse>> getRoomByBuilding(@PathVariable("id") String building){
        return ApiResponse.<List<RoomResponse>>builder()
                .data(roomService.getRoomByBuilding(building))
                .build();
    }

    @GetMapping("/type/{id}")
    ApiResponse<List<RoomResponse>> getRoomByType(@PathVariable("id") String type){
        return ApiResponse.<List<RoomResponse>>builder()
                .data(roomService.getRoomByType(type))
                .build();
    }

    //Get Room By Status
    @GetMapping("/status")
    ApiResponse<List<RoomResponse>> getRoomByStatus(){
        return ApiResponse.<List<RoomResponse>>builder()
                .data(roomService.getRoomByStatus())
                .build();
    }

    //Get booked Slot
    @GetMapping("/{roomName}/booked-slot")
    ApiResponse<Map<String, List<LocalDate>>> getBookedSlot(@PathVariable String roomName){
        return ApiResponse.<Map<String, List<LocalDate>>>builder()
                .data(roomService.getBookedSlot(roomName))
                .build();
    }
}
