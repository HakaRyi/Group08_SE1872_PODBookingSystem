package com.example.POD_BookingSystem.DTO.Request.User;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserUpdateRequest {
    @NotBlank(message = "NAME_CANNOT_BE_BLANK")
    private String name;
    @NotBlank(message = "PASSWORD_CANNOT_BE_BLANK")
    @Size(min = 8, message = "PASSWORD_INVALID")
    private String password;
    @Pattern(regexp = "0[0-9]{9}", message = "PHONE_INVALID")
    private String phone;
    @NotBlank(message = "EMAIL_CANNOT_BE_BLANK")
    @Email(message = "EMAIL_INVALID")
    private String email;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
