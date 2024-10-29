package com.example.POD_BookingSystem.Entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "Role")
public class Role {
    @Id
    String role_id;
    String roleName;
    // Mối quan hệ 1-nhiều với bảng User
    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL, orphanRemoval = true)
    List<User> users;


}