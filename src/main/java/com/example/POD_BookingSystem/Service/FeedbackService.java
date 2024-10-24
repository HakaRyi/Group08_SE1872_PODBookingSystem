package com.example.POD_BookingSystem.Service;

import com.example.POD_BookingSystem.DTO.Request.FeedBack.CreateFeedbackRequest;
import com.example.POD_BookingSystem.DTO.Response.FeedbackResponse;
import com.example.POD_BookingSystem.DTO.Response.RoomResponse;
import com.example.POD_BookingSystem.Entity.EBooking.Booking;
import com.example.POD_BookingSystem.Entity.ERoom.Room;
import com.example.POD_BookingSystem.Entity.FeedBack;
import com.example.POD_BookingSystem.Entity.User;
import com.example.POD_BookingSystem.Exception.AppException;
import com.example.POD_BookingSystem.Exception.ErrorCode;
import com.example.POD_BookingSystem.Mapper.FeedbackMapper;
import com.example.POD_BookingSystem.Repository.FeedbackRepository;
import com.example.POD_BookingSystem.Repository.ReBooking.BookingRepository;
import com.example.POD_BookingSystem.Repository.ReRoom.RoomRepository;
import com.example.POD_BookingSystem.Repository.UserRepository;
import com.google.api.client.util.DateTime;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class FeedbackService {
    @Autowired
    FeedbackRepository feedbackRepository;

    @Autowired
    RoomRepository roomRepository;

    @Autowired
    FeedbackMapper feedbackMapper;

    @Autowired
    UserRepository userRepository;

    private String GenerateId(){
        List<FeedBack> feedBacks = feedbackRepository.findAll();
        int number = 0;
        if(feedBacks.isEmpty()){
            number = 1;
        }else{
            number = feedBacks.size() + 1;
        }
        return String.format("FB-%02d", number);
    }

    //FEED BACK CREATION
    public FeedbackResponse createFeedback(CreateFeedbackRequest request, String roomName, String username){
        Room room = roomRepository.findByName(roomName);
        if(room == null) throw new AppException(ErrorCode.NAME_NOT_FOUND);
        log.info(room.getName());

        User user = userRepository.findByUsername(username).orElseThrow(() -> new AppException(ErrorCode.NAME_NOT_FOUND));
        log.info(user.getName());
        FeedBack feedBack = FeedBack.builder()
                .feedbackId(GenerateId())
                .user(user)
                .room(room)
                .description(request.getDescription())
                .rating(request.getRating())
                .timestamp(LocalDateTime.now())
                .build();

        feedbackRepository.save(feedBack);

        FeedbackResponse response = feedbackMapper.toFeedbackResponse(feedBack);
        response.setUserName(username);
        response.setRoomName(roomName);
        return response;
    }

    public List<FeedbackResponse> getFeedBackByRoom(String roomName){
        Room room = roomRepository.findByName(roomName);
        if(room == null) throw new AppException(ErrorCode.NAME_NOT_FOUND);

        List<FeedBack> feedBacks = feedbackRepository.findByRoom(room.getRoom_id());
        return feedBacks.stream()
                .map(feedbackMapper::toFeedbackResponse)
                .toList();
    }
}
