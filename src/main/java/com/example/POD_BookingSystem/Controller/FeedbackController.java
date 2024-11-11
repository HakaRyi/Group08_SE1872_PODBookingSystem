package com.example.POD_BookingSystem.Controller;

import com.example.POD_BookingSystem.DTO.Request.FeedBack.CreateFeedbackRequest;
import com.example.POD_BookingSystem.DTO.Response.ApiResponse;
import com.example.POD_BookingSystem.DTO.Response.FeedbackResponse;
import com.example.POD_BookingSystem.Service.AuthenticationService;
import com.example.POD_BookingSystem.Service.FeedbackService;
import com.nimbusds.jose.JOSEException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/feedback")
public class FeedbackController {
    @Autowired
    FeedbackService feedbackService;
    @Autowired
    AuthenticationService authenticationService;

    //Create Building API
    @PostMapping("/{roomName}")
    ApiResponse<FeedbackResponse> createFeedback(@PathVariable String roomName,
                                                 @RequestBody CreateFeedbackRequest request,
                                                HttpServletRequest servletRequest) throws ParseException, JOSEException {
        String token = servletRequest.getHeader("Authorization").substring(7); // Bỏ qua "Bearer " trong token
        String username = authenticationService.getUsernameFromToken(token);
        return ApiResponse.<FeedbackResponse>builder()
                .data(feedbackService.createFeedback(request, roomName, username))
                .build();
    }

//    //Get All Building API
//    @GetMapping
//    ApiResponse<List<BuildingResponse>> getBuildings(){
//        return ApiResponse.<List<BuildingResponse>>builder()
//                .data(feedbackService.getAllBuildings())
//                .build();
//    }
//
    //Get FeedBack By RoomName
    @GetMapping("/{roomName}")
    ApiResponse<List<FeedbackResponse>> getFeedbackByRoom(@PathVariable String name){
        return ApiResponse.<List<FeedbackResponse>>builder()
                .data(feedbackService.getFeedBackByRoom(name))
                .build();
    }
//
//    //Get Building By Location API
//    @GetMapping("/location/{name}")
//    ApiResponse<List<BuildingResponse>> getBuildingByLocation(@PathVariable("name") String name){
//        return ApiResponse.<List<BuildingResponse>>builder()
//                .data(feedbackService.getBuildingsByLocation(name))
//                .build();
//    }
//
//    //Update Building API
//    @PutMapping("/{id}")
//    ApiResponse<BuildingResponse> updateBuilding(@PathVariable String id, @RequestBody UpdateBuildingRequest request) {
//        return ApiResponse.<BuildingResponse>builder().data(feedbackService.updateBuilding(id, request)).build();
//    }
//
//    //Delete Building API
//    @DeleteMapping("/{id}")
//    ApiResponse<Void> deleteBuilding(@PathVariable String id) {
//        feedbackService.deleteBuilding(id);
//        return ApiResponse.<Void>builder().message("Delete Successfully !!!").build();
//    }

}
