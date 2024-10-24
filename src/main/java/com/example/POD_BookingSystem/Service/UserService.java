package com.example.POD_BookingSystem.Service;
import com.example.POD_BookingSystem.DTO.Request.User.UserUpdateRequest;
import com.example.POD_BookingSystem.Entity.Role;
import com.example.POD_BookingSystem.Mapper.UserMapper;
import jakarta.servlet.http.HttpServletRequest;
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
    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserMapper userMapper;

    public UserResponse createUser(UserCreationRequest request){
            Role role=roleRepository.findById("Role-04").orElseThrow();
        if(userRepository.existsByUsername(request.getUsername()))   //kiểm tra user tồn tại hay ko
            throw new AppException(ErrorCode.USER_EXISTED);
        if(userRepository.existsByPhone(request.getPhone()))   //kiểm tra phone tồn tại hay ko
            throw new AppException(ErrorCode.PHONE_EXISTED);
        if(userRepository.existsByEmail(request.getEmail()))   //kiểm tra email tồn tại hay ko
            throw new AppException(ErrorCode.EMAIL_EXISTED);
        if ( !request.getPassword().equals(request.getConfirmPassword())) {
            throw new AppException(ErrorCode.PASSWORDS_DO_NOT_MATCH);
        }
        User user = userMapper.toUser(request);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setUserid_id(GenerateId());
        user.setRole_id(role);
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
        user.setRole_id(role);
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
        user.setRole_id(role);
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

        return UserResponse.builder()
                .userid_id(user.getUserid_id())
                .name(user.getName())
                .username(user.getUsername())
                .phone(user.getPhone())
                .email(user.getEmail())
                .build();

    }

    public void deleteUser(String userId){
        userRepository.deleteById(userId);
    }


    //Get My Info: lấy thông tin user bằng token mà ko cần tham số ngoài
    public UserResponse getMyInfo(){
        var context = SecurityContextHolder.getContext();
        String phone=context.getAuthentication().getName();

        User user = userRepository.findByPhone(phone).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
       return userMapper.toUserResponse(userRepository.save(user));


    }
    private String GenerateId(){
        String id = userRepository.findLastId();
        if(!(id == null)){
            int number = Integer.parseInt(id.substring(2))+1;
            return String.format("U-%02d", number);
        }
        return "U-01";
    }


}