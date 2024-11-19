package com.example.POD_BookingSystem.Controller;

import com.example.POD_BookingSystem.DTO.Response.ApiResponse;
import com.example.POD_BookingSystem.DTO.Response.DashBoardResponse;
import com.example.POD_BookingSystem.Service.DashBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/dashboard")
public class DashBoardController {
    @Autowired
    DashBoardService dashBoardService;


    @GetMapping("/information")
    public ApiResponse<DashBoardResponse> getDashBoardInformation(){
        return ApiResponse.<DashBoardResponse>builder().data(dashBoardService.getDashBoardInformation()).build();
    }
}

