package com.example.POD_BookingSystem.DTO.Request.User;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserUpdateRequest {
    @NotBlank(message = "NAME_CANNOT_BE_BLANK")
    private String name;

    @NotBlank(message = "PASSWORD_CANNOT_BE_BLANK")
    @Size(min = 8, message = "PASSWORD_INVALID")
    private String password;

    private String phone;

    @NotBlank(message = "EMAIL_CANNOT_BE_BLANK")
    private String email;

}
