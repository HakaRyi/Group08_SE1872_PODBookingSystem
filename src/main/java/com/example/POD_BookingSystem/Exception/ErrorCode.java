package com.example.POD_BookingSystem.Exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_KEY(1001, "Invalid message key", HttpStatus.BAD_REQUEST),
    USER_EXISTED(1002, "User existed",HttpStatus.BAD_REQUEST),
    USERNAME_INVALID(1003, "Username must be at least 3 characters",HttpStatus.BAD_REQUEST),
    PASSWORD_INVALID(1004, "Password must be at least 8 characters",HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED(1005, "User not existed",HttpStatus.NOT_FOUND),
    UNAUTHENTICATED(1006, "Unauthenticated",HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1007, "You do not have permission",HttpStatus.FORBIDDEN),
    ID_NOT_FOUND(1008, "ID is not existed or not available", HttpStatus.NOT_FOUND),
    NAME_NOT_FOUND(1009, "Wrong name or name is not existed", HttpStatus.NOT_FOUND),
    INVALID_DATE_RANGE(1010,"Invalid date range",HttpStatus.NOT_FOUND),
   // IMAGE_NOT_FOUND(1010,"Cannot found the image",HttpStatus.NOT_FOUND),
//    ROOM_NOT_AVAILABLE(1011,"Room not available",HttpStatus.NOT_FOUND)
NAME_CANNOT_BE_BLANK(1011, "Name cannot be blank",HttpStatus.BAD_REQUEST),
    USERNAME_CANNOT_BE_BLANK(1012, "Username cannot be blank",HttpStatus.BAD_REQUEST),
    PASSWORD_CANNOT_BE_BLANK(1013, "Password cannot be blank",HttpStatus.BAD_REQUEST),
    PHONE_INVALID(1014, "Invalid phone number",HttpStatus.BAD_REQUEST),
    EMAIL_CANNOT_BE_BLANK(1015, "Email cannot be blank",HttpStatus.BAD_REQUEST),
    EMAIL_INVALID(1016, "Invalid email",HttpStatus.BAD_REQUEST),
    PHONE_EXISTED(1017, "Phone existed",HttpStatus.BAD_REQUEST),
    EMAIL_EXISTED(1018, "Email existed",HttpStatus.BAD_REQUEST),
    CONFIRM_PASSWORD_CANNOT_BE_BLANK(1019, "Confirm password cannot be blank",HttpStatus.BAD_REQUEST),
    PASSWORDS_DO_NOT_MATCH(1020, "Password do not match",HttpStatus.BAD_REQUEST),
    ;

    private int code;
    private String message;
    private HttpStatusCode httpStatusCode;

    ErrorCode(int code, String message, HttpStatusCode httpStatusCode) {
        this.code = code;
        this.message = message;
        this.httpStatusCode = httpStatusCode;
    }

}
