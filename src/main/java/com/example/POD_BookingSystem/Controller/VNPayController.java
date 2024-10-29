package com.example.POD_BookingSystem.Controller;

import com.example.POD_BookingSystem.DTO.Request.VNPay.RefundRequest;
import com.example.POD_BookingSystem.DTO.Request.VNPay.VNPayRequest;
import com.example.POD_BookingSystem.DTO.Response.ApiResponse;
import com.example.POD_BookingSystem.Entity.Refund;
import com.example.POD_BookingSystem.Service.VNPayService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;


@RestController
public class VNPayController {
    @Autowired
    VNPayService vnPayService;

    @PostMapping("/{bookingId}/api/payment")
    ResponseEntity<?> createPayment(@RequestBody VNPayRequest vnPayRequest,
                                    @PathVariable String bookingId,
                                    HttpServletRequest request) throws UnsupportedEncodingException {
        return ResponseEntity.status(HttpStatus.OK).body(vnPayService.createPayment(vnPayRequest,bookingId,request));
    }

    @PostMapping("/refund")
    public ApiResponse<Void> handleRefund(HttpServletRequest req, @RequestBody RefundRequest request) throws Exception {
        if(vnPayService.requestRefund(request, req)){
            return ApiResponse.<Void>builder()
                    .message("Refund Successfull")
                    .build();
        }else {
            return ApiResponse.<Void>builder()
                    .message("Refund Fail")
                    .build();
        }
    }

    @GetMapping("/refund-request")
    public List<Refund> allRefundRequest(){
        return vnPayService.getRefundList();
    }
}

