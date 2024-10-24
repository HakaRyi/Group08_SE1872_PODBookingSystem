package com.example.POD_BookingSystem.Controller;


import com.example.POD_BookingSystem.DTO.Request.Authentication.AuthenticationRequest;
import com.example.POD_BookingSystem.DTO.Request.Authentication.GetUserInfoRequest;
import com.example.POD_BookingSystem.DTO.Request.Authentication.IntrospectRequest;
import com.example.POD_BookingSystem.DTO.Request.Authentication.LogoutRequest;
import com.example.POD_BookingSystem.DTO.Response.ApiResponse;
import com.example.POD_BookingSystem.DTO.Response.AuthenticationResponse;
import com.example.POD_BookingSystem.DTO.Response.IntrospectResponse;
import com.example.POD_BookingSystem.Service.AuthenticationService;

import com.nimbusds.jose.JOSEException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {
    @Autowired
    AuthenticationService authenticationService;

    @PostMapping("/log-in")
    ApiResponse<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request){
        var result = authenticationService.authenticate(request);
        return ApiResponse.<AuthenticationResponse>builder()
                .data(result)
                .build();
    }
    @PostMapping("/log-out")
    ApiResponse<Void> authenticate(HttpServletRequest request) throws ParseException, JOSEException {
        String token = request.getHeader("Authorization").substring(7);
       // String username = authenticationService.getUsernameFromToken(token);
        authenticationService.logout(token);
        return ApiResponse.<Void>builder()
                .build();
    }
    @PostMapping("/introspect")
    ApiResponse<IntrospectResponse> authenticate(@RequestBody IntrospectRequest request) throws ParseException, JOSEException {
        var result = authenticationService.introspect(request);
        return ApiResponse.<IntrospectResponse>builder()
                .data(result)
                .build();
    }

}