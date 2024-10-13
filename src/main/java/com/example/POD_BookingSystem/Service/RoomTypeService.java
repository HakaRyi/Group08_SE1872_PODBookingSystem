package com.example.POD_BookingSystem.Service;

import com.example.POD_BookingSystem.DTO.Request.RoomType.CreateRoomTypeRequest;
import com.example.POD_BookingSystem.DTO.Response.RoomTypeResponse;
import com.example.POD_BookingSystem.Entity.ERoom.Room_Type;
import com.example.POD_BookingSystem.Mapper.RoomTypeMapper;
import com.example.POD_BookingSystem.Repository.BuildingRepository;
import com.example.POD_BookingSystem.Repository.ReRoom.RoomTypeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class RoomTypeService {
    @Autowired
    RoomTypeRepository roomTypeRepository;
    @Autowired
    BuildingRepository buildingRepository;
    @Autowired
    RoomTypeMapper roomTypeMapper;

    // Tao Ra 1 Room Type MOI
    public RoomTypeResponse createRoomType (CreateRoomTypeRequest request){
        log.info("At Service");
        log.info(request.getName());
        Room_Type roomType = Room_Type.builder()
                .type_id(GenerateId())
                .name(request.getName())
                .build();
        log.info(roomType.getName());
        roomTypeRepository.save(roomType);
        RoomTypeResponse roomTypeResponse = roomTypeMapper.toRoomTypeResponse(roomType);
        log.info(roomTypeResponse.getName());
        return roomTypeResponse;
    }

    // TAO ID 1 cach tu dong
    private String GenerateId(){
        String id = roomTypeRepository.findLastId();
        if(!(id == null)){
            int number = Integer.parseInt(id.substring(2))+1;
            return String.format("T-%02d", number);
        }
        return "T-01";
    }

    //Get All RoomType
    public List<RoomTypeResponse> getAllRoomTypes(){
        List<Room_Type> roomTypes = roomTypeRepository.findAll();
        return  roomTypes.stream().map(roomTypeMapper::toRoomTypeResponse).collect(Collectors.toList());
    }


//    //Update Building
//    public BuildingResponse updateBuilding(String id, UpdateBuildingRequest request){
//        Building building = buildingRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.ID_NOT_FOUND));
//        buildingMapper.updateBuilding(building, request);
//        buildingRepository.save(building);
//        return buildingMapper.toBuildingResponse(building);
//    }
//
//    //Delete Building
//    public void deleteBuilding(String id){
//        buildingRepository.deleteById(id);
//    }
}
