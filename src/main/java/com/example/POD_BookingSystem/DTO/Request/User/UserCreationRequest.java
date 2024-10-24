package com.example.POD_BookingSystem.DTO.Request.User;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
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
    @NotBlank(message = "CONFIRM_PASSWORD_CANNOT_BE_BLANK")
    String confirmPassword;
    @NotBlank(message = "PHONE_INVALID")
    @Pattern(regexp = "0[0-9]{9}", message = "PHONE_INVALID")
    String phone;
    @NotBlank(message = "EMAIL_CANNOT_BE_BLANK")
    @Email(message = "EMAIL_INVALID")
    String email;

}