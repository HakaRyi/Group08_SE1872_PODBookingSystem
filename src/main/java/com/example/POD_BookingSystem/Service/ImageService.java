package com.example.POD_BookingSystem.Service;

import com.example.POD_BookingSystem.DTO.Request.Building.CreateBuildingRequest;
import com.example.POD_BookingSystem.DTO.Request.Image.CreateImageRequest;
import com.example.POD_BookingSystem.DTO.Response.BuildingResponse;
import com.example.POD_BookingSystem.DTO.Response.ImageResponse;
import com.example.POD_BookingSystem.Entity.Building;
import com.example.POD_BookingSystem.Entity.ImageBuilding;
import com.example.POD_BookingSystem.Exception.AppException;
import com.example.POD_BookingSystem.Exception.ErrorCode;
import com.example.POD_BookingSystem.Mapper.ImageMapper;
import com.example.POD_BookingSystem.Repository.BuildingRepository;
import com.example.POD_BookingSystem.Repository.ImageRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ImageService {
    @Autowired
    ImageRepository imageRepository;
    @Autowired
    ImageMapper imageMapper;
    @Autowired
    BuildingRepository buildingRepository;

    // TẠO 1 ImageBuilding mới
    public BuildingResponse createImageBuilding (CreateImageRequest request){
        String building_name = request.getProduct_name();
        //if(buildingRepository.existsByName(building_name)) throw new RuntimeException("Building is existed");
        if(building_name==null)
            throw new AppException(ErrorCode.NAME_NOT_FOUND);
        ImageBuilding imageBuilding = ImageBuilding.builder()
                .image_building_id(GenerateId())
                .building(building_name)
                .image(request.getImage())
                .alt_text(request.getAlt_text())
                .build();
        imageRepository.save(imageBuilding);
        ImageResponse result = imageMapper.toImageResponse(imageBuilding);
        result.
        return imageMapper.toImageResponse(imageBuilding);
    }
    // TAO ID 1 cach tu dong
    private String GenerateId(){
        String id = imageRepository.findLastId();
        if(!(id == null)){
            int number = Integer.parseInt(id.substring(3))+1;
            return String.format("IB-%02d", number);
        }
        return "IB-01";
    }
}
