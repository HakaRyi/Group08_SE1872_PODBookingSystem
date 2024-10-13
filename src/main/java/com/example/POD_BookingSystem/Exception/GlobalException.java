package com.example.POD_BookingSystem.Exception;

import com.example.POD_BookingSystem.DTO.Response.ApiResponse;
import com.example.POD_BookingSystem.Exception.ErrorCode;
//import com.example.identity_service.dto.request.ApiResponse;
//import jakarta.validation.ConstraintViolation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;
import java.util.Objects;

@Slf4j
@ControllerAdvice
public class GlobalException {

    private static final String MIN_ATTRIBUTE = "min";

//    @ExceptionHandler(value = Exception.class)
//    ResponseEntity<ApiResponse> handlingException(RuntimeException exception){
//        ErrorCode errorCode = ErrorCode.UNCATEGORIZED;
//        ApiResponse apiResponse = new ApiResponse<>();
//        apiResponse.setCode(9999);
//        apiResponse.setMessage(ErrorCode.UNCATEGORIZED.getMessage());
//        return ResponseEntity.badRequest().body(apiResponse);
//    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiResponse<String>> handlingException(RuntimeException ex) {
        ApiResponse<String> response = ApiResponse.<String>builder()
                .message(ex.getMessage())
                .build();
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = AppException.class)
    ResponseEntity<ApiResponse> handlingAppException(AppException exception) {
        ApiResponse apiResponse = new ApiResponse();
        ErrorCode errorCode = exception.getErrorCode();

        apiResponse.setCode(errorCode.getCode());
        apiResponse.setMessage(errorCode.getMessage());

        return ResponseEntity.status(errorCode.getHttpStatusCode()).body(apiResponse);
    }
//
//
//    @ExceptionHandler(value = MethodArgumentNotValidException.class)
//    ResponseEntity<ApiResponse> handlingValidation(MethodArgumentNotValidException exception) {
//        String enumKey = exception.getFieldError().getDefaultMessage();
//        log.info(enumKey);
//        ErrorCode errorCode = null;
//        Map<String,Object> constraintAttribute = null;
//        errorCode = ErrorCode.INVALID_KEY;
//        try {
//            errorCode = ErrorCode.valueOf(enumKey);
//
//            var constraintViolation = exception.getBindingResult().getAllErrors().getFirst().unwrap(ConstraintViolation.class);
//            constraintAttribute = constraintViolation.getConstraintDescriptor().getAttributes();
//            log.info(constraintAttribute.toString());
//        } catch (Exception e) {
//
//        }
//        ApiResponse apiResponse = new ApiResponse();
//        apiResponse.setCode(errorCode.getCode());
//        apiResponse.setMessage(Objects.nonNull(constraintAttribute)?
//                mapAtribute(errorCode.getMessage(), constraintAttribute):
//                errorCode.getMessage());
//        return ResponseEntity.badRequest().body(apiResponse);
//    }
//
//    @ExceptionHandler(AccessDeniedException.class)
//    ResponseEntity<ApiResponse> handlingAccessDeniedException(AccessDeniedException exception) {
//        System.out.println("Access denied: " + exception.getMessage());
//        ErrorCode errorCode = ErrorCode.UNAUTHORIZED;
//        return ResponseEntity.status(errorCode.getHttpStatusCode()).body(
//                ApiResponse.builder()
//                        .code(errorCode.getCode())
//                        .message(errorCode.getMessage())
//                        .build()
//        );
//    }
//
//    private String mapAtribute(String message, Map<String, Object> attribute){
//        String minValue = String.valueOf(attribute.get(MIN_ATTRIBUTE));
//        return message.replace("{"+MIN_ATTRIBUTE+"}", minValue);
//    }
}
