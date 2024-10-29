package com.example.POD_BookingSystem.Service;
import com.example.POD_BookingSystem.DTO.Request.User.UserUpdateRequest;
import com.example.POD_BookingSystem.Entity.Role;
import com.example.POD_BookingSystem.Mapper.UserMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.example.POD_BookingSystem.DTO.Request.User.UserCreationRequest;
import com.example.POD_BookingSystem.DTO.Response.UserResponse;
import com.example.POD_BookingSystem.Entity.User;
import com.example.POD_BookingSystem.enums.VIP;
import com.example.POD_BookingSystem.Exception.AppException;
import com.example.POD_BookingSystem.Exception.ErrorCode;
import com.example.POD_BookingSystem.Repository.UserRepository;
import com.example.POD_BookingSystem.Repository.RoleRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserService {
    @Autowired
    UserRepository userRepository;
    RoleRepository roleRepository;

    @Autowired
    UserMapper userMapper;

    public UserResponse createUser(UserCreationRequest request){
        Role role=roleRepository.findById("Role-04").orElseThrow();
        if(userRepository.existsByUsername(request.getUsername()))   //kiểm tra user tồn tại hay ko
            throw new AppException(ErrorCode.USER_EXISTED);

        if(request.getPhone() != null && userRepository.existsByPhone(request.getPhone()))   //kiểm tra phone tồn tại hay ko
            throw new AppException(ErrorCode.PHONE_EXISTED);

        if(userRepository.existsByEmail(request.getEmail()))   //kiểm tra email tồn tại hay ko
            throw new AppException(ErrorCode.EMAIL_EXISTED);
        User user = userMapper.toUser(request);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setUserid_id(GenerateId());
        user.setRole(role);
        user.setVIP(VIP.INACTIVE.name());
        return userMapper.toUserResponse(userRepository.save(user));

    }

    public UserResponse createStaff(UserCreationRequest request){
        Role role=roleRepository.findById("Role-03").orElseThrow();
        if(userRepository.existsByUsername(request.getUsername()))   //kiểm tra user tồn tại hay ko
            throw new AppException(ErrorCode.USER_EXISTED);
        if(request.getPhone() != null && userRepository.existsByPhone(request.getPhone()))   //kiểm tra phone tồn tại hay ko
            throw new AppException(ErrorCode.PHONE_EXISTED);
        if(userRepository.existsByEmail(request.getEmail()))   //kiểm tra email tồn tại hay ko
            throw new AppException(ErrorCode.EMAIL_EXISTED);
        User user = userMapper.toUser(request);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setUserid_id(GenerateId());
        user.setRole(role);
        user.setVIP(VIP.INACTIVE.name());
        return userMapper.toUserResponse(userRepository.save(user));
    }

    public UserResponse createManager(UserCreationRequest request){
        Role role=roleRepository.findById("Role-02").orElseThrow();
        if(userRepository.existsByUsername(request.getUsername()))   //kiểm tra user tồn tại hay ko
            throw new AppException(ErrorCode.USER_EXISTED);
        if(request.getPhone() != null && userRepository.existsByPhone(request.getPhone()))   //kiểm tra phone tồn tại hay ko
            throw new AppException(ErrorCode.PHONE_EXISTED);
        if(userRepository.existsByEmail(request.getEmail()))   //kiểm tra email tồn tại hay ko
            throw new AppException(ErrorCode.EMAIL_EXISTED);
        User user = userMapper.toUser(request);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setUserid_id(GenerateId());
        user.setRole(role);
        user.setVIP(VIP.INACTIVE.name());
        return userMapper.toUserResponse(userRepository.save(user));
    }


    public List<UserResponse> getUsers() {
        log.info("In method get Users");
        return userRepository.findAll().stream().map(userMapper::toUserResponse).toList();
    }

    public UserResponse getUser(String id) {
        log.info("In method get user by Id");
        return userMapper.toUserResponse(
                userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED)));
    }

    public UserResponse updateUser(String userId, UserUpdateRequest request){
        User user=userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        userMapper.updateUser(user,request);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        return userMapper.toUserResponse(userRepository.save(user));

    }

    public void deleteUser(String userId){
        userRepository.deleteById(userId);
    }


    //Get My Info: lấy thông tin user bằng token mà ko cần tham số ngoài
    public UserResponse getMyInfo(){
        var context = SecurityContextHolder.getContext();
        String name=context.getAuthentication().getName();

        User user = userRepository.findByUsername(name).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        return userMapper.toUserResponse(user);
    }
    private String GenerateId(){
        String id = userRepository.findLastId();
        if(!(id == null)){
            int number = Integer.parseInt(id.substring(3))+1;
            return String.format("U-%02d", number);
        }
        return "U-01";
    }

}