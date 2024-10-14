package com.example.POD_BookingSystem.Controller;


import com.example.POD_BookingSystem.DTO.Request.Room.AddServiceRequest;
import com.example.POD_BookingSystem.DTO.Request.Room.CreateRoomRequest;
import com.example.POD_BookingSystem.DTO.Request.Room.UpdateRoomRequest;
import com.example.POD_BookingSystem.DTO.Request.Slot.CreateSlotRequest;
import com.example.POD_BookingSystem.DTO.Request.Slot.UpdateSlotRequest;
import com.example.POD_BookingSystem.DTO.Response.ApiResponse;
import com.example.POD_BookingSystem.DTO.Response.RoomResponse;
import com.example.POD_BookingSystem.DTO.Response.SlotResponse;
import com.example.POD_BookingSystem.Entity.Slot;
import com.example.POD_BookingSystem.Service.RoomService;
import com.example.POD_BookingSystem.Service.SlotService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/slots")
public class SlotController {
    @Autowired
    SlotService slotService;

    //Create Slot API
    @PostMapping
    ApiResponse<SlotResponse> createSlot(@RequestBody CreateSlotRequest request){
        log.info(request.getSlot_id());
        return ApiResponse.<SlotResponse>builder()
                .data(slotService.createSlot(request))
                .build();
    }

    //Get All Slot API
    @GetMapping
    ApiResponse<List<SlotResponse>> getBuildings(){
        return ApiResponse.<List<SlotResponse>>builder()
                .data(slotService.getAllSlot())
                .build();
    }

    //Get Slot By Id API
    @GetMapping("/{id}")
    ApiResponse<Slot> getBuilding(@PathVariable String id){
        return ApiResponse.<Slot>builder()
                .data(slotService.getSlot(id))
                .build();
    }

    //Update Slot API
    @PutMapping("/{id}")
    ApiResponse<SlotResponse> updateRoom(@PathVariable String id, @RequestBody UpdateSlotRequest request) {
        return ApiResponse.<SlotResponse>builder().data(slotService.updateSlot(id, request)).build();
    }

    //Delete Slot API
    @DeleteMapping("/{id}")
    ApiResponse<Void> deleteSlot(@PathVariable String id) {
        slotService.deleteSlot(id);
        return ApiResponse.<Void>builder().message("Delete Successfully !!!").build();
    }

}
