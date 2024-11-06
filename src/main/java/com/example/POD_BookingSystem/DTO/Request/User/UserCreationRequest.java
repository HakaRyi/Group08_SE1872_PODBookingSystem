package com.example.POD_BookingSystem.DTO.Request.User;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreationRequest {

    @NotBlank(message = "NAME_CANNOT_BE_BLANK")
    String name;

    @NotBlank(message = "USERNAME_CANNOT_BE_BLANK")
    @Size(min = 3, message = "USERNAME_INVALID")
    String username;

    @NotBlank(message = "PASSWORD_CANNOT_BE_BLANK")
    @Size(min = 8, message = "PASSWORD_INVALID")
    String password;

    String phone;

    @NotBlank(message = "EMAIL_CANNOT_BE_BLANK")
    String email;

}