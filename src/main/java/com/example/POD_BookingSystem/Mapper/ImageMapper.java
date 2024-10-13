package com.example.POD_BookingSystem.Mapper;

import com.example.POD_BookingSystem.DTO.Request.Image.CreateImageRequest;
import com.example.POD_BookingSystem.DTO.Request.Image.UpdateImageRequest;
import com.example.POD_BookingSystem.DTO.Response.ImageResponse;
import com.example.POD_BookingSystem.Entity.Image;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ImageMapper {
    Image toImage(CreateImageRequest request);

    ImageResponse toImageResponse(Image image);

    @Mapping(target = "image_id", ignore = true)
    void updateImage(@MappingTarget Image image, UpdateImageRequest request);
}
