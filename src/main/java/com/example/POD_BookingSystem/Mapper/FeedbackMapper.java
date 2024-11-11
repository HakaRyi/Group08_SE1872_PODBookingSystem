package com.example.POD_BookingSystem.Mapper;

import com.example.POD_BookingSystem.DTO.Response.FeedbackResponse;
import com.example.POD_BookingSystem.Entity.FeedBack;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FeedbackMapper {
    FeedbackResponse toFeedbackResponse(FeedBack feedBack);
}
