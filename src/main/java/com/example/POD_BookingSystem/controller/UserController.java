package com.example.POD_BookingSystem.Controller;

import com.example.POD_BookingSystem.DTO.Request.User.UserCreationRequest;
import com.example.POD_BookingSystem.DTO.Request.User.UserUpdateRequest;
import com.example.POD_BookingSystem.DTO.Response.ApiResponse;
import com.example.POD_BookingSystem.DTO.Response.UserResponse;
import com.example.POD_BookingSystem.Service.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping
    ApiResponse<UserResponse> createUser(@RequestBody @Valid UserCreationRequest request){

        ApiResponse<UserResponse> apiResponse=new ApiResponse<>();

        apiResponse.setData(userService.createUser(request));

        return apiResponse;
    }
    @GetMapping
    ApiResponse<List<UserResponse>> getUsers() {
        return ApiResponse.<List<UserResponse>>builder()
                .data(userService.getUsers())
                .build();
    }
    @GetMapping("/{userId}")
    ApiResponse<UserResponse> getUser(@PathVariable("userId") String userId) {
        return ApiResponse.<UserResponse>builder()
                .data(userService.getUser(userId))
                .build();
    }

    @PutMapping("/{userId}")
    ApiResponse<UserResponse> updateUser(@PathVariable String userId, @RequestBody UserUpdateRequest request) {
        return ApiResponse.<UserResponse>builder()
                .data(userService.updateUser(userId, request))
                .build();
    }

    @DeleteMapping("/{userId}")
    ApiResponse<String> deleteUser(@PathVariable String userId) {
        userService.deleteUser(userId);
        return ApiResponse.<String>builder()
                .data("User has been deleted")
                .build();
    }

    @GetMapping("/myinfo")
    ApiResponse<UserResponse> getMyInfo() {
        return ApiResponse.<UserResponse>builder()
                .data(userService.getMyInfo())
                .build();
    }

}