package com.example.POD_BookingSystem.Controller;

import com.example.POD_BookingSystem.DTO.Request.Room.CreateRoomRequest;
import com.example.POD_BookingSystem.DTO.Request.Room.UpdateRoomRequest;
import com.example.POD_BookingSystem.DTO.Request.Service.CreateServiceRequest;
import com.example.POD_BookingSystem.DTO.Request.Service.UpdateServiceRequest;
import com.example.POD_BookingSystem.DTO.Response.ApiResponse;
import com.example.POD_BookingSystem.DTO.Response.RoomResponse;
import com.example.POD_BookingSystem.DTO.Response.ServiceResponse;
import com.example.POD_BookingSystem.Service.RoomService;
import com.example.POD_BookingSystem.Service.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/services")
public class ServiceController {
    @Autowired
    RoomService roomService;
    @Autowired
    ServiceService serviceService;

    //Create Service API
    @PostMapping
    ApiResponse<ServiceResponse> createService(@RequestBody CreateServiceRequest request){
        return ApiResponse.<ServiceResponse>builder()
                .data(serviceService.createService(request))
                .build();
    }

    //Get All Service API
    @GetMapping
    ApiResponse<List<ServiceResponse>> getServices(){
        return ApiResponse.<List<ServiceResponse>>builder()
                .data(serviceService.getAllService())
                .build();
    }

    //Get Service By Name API
    @GetMapping("/{name}")
    ApiResponse<List<ServiceResponse>> getService(@PathVariable String name){
        return ApiResponse.<List<ServiceResponse>>builder()
                .data(serviceService.getServices(name))
                .build();
    }

    //Update Service API
    @PutMapping("/{id}")
    ApiResponse<ServiceResponse> updateService(@PathVariable String id, @RequestBody UpdateServiceRequest request) {
        return ApiResponse.<ServiceResponse>builder().data(serviceService.updateService(id, request)).build();
    }

    //Delete Service API
    @DeleteMapping("/{id}")
    ApiResponse<Void> deleteService(@PathVariable String id) {
        serviceService.deleteService(id);
        return ApiResponse.<Void>builder().message("Delete Successfully !!!").build();
    }

}
