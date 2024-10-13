package com.example.POD_BookingSystem.Service;
import com.example.POD_BookingSystem.DTO.Request.User.StaffBuildingRequest;
import com.example.POD_BookingSystem.DTO.Response.StaffBuildingResponse;
import com.example.POD_BookingSystem.Entity.EBuilding.Building;
import com.example.POD_BookingSystem.Entity.EBuilding.Staff_Building;
import com.example.POD_BookingSystem.Entity.User;
import com.example.POD_BookingSystem.Exception.AppException;
import com.example.POD_BookingSystem.Exception.ErrorCode;
import com.example.POD_BookingSystem.Mapper.StaffBuildingMapper;
import com.example.POD_BookingSystem.Repository.BuildingRepository;
import com.example.POD_BookingSystem.Repository.StaffBuildingRepository;
import com.example.POD_BookingSystem.Repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class StaffBuildingService {
    @Autowired
    StaffBuildingRepository staffBuildingRepository;
    @Autowired
    StaffBuildingMapper staffBuildingMapper;
    @Autowired
    BuildingRepository buildingRepository;
    @Autowired
    UserRepository userRepository;

    public StaffBuildingResponse createStaffBuilding(StaffBuildingRequest request){
        Building building=buildingRepository.findByName(request.getName());
        if(building==null)
            throw new AppException(ErrorCode.NAME_NOT_FOUND);
        User user = userRepository.findName(request.getUsername());
        if(user == null)
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        if (request.getEnd_date().isBefore(request.getStart_date()))
            throw new AppException(ErrorCode.INVALID_DATE_RANGE);
        Staff_Building staffBuilding=Staff_Building.builder()
                .staff_building_id(GenerateId())
                .building(building)
                .user(user)
                .start_date(request.getStart_date())
                .end_date(request.getEnd_date())
                .build();
        staffBuildingRepository.save(staffBuilding);
        StaffBuildingResponse result = staffBuildingMapper.toStaffBuildingResponse(staffBuilding);
        result.setBuilding_id(building.getBuilding_id());
        result.setUserid_id(user.getUserid_id());
        return result;

    }



    private String GenerateId(){
        String id = staffBuildingRepository.findLastId();
        if(!(id == null)){
            int number = Integer.parseInt(id.substring(4))+1;
            return String.format("SB-%02d", number);
        }
        return "SB-01";
    }


    public List<StaffBuildingResponse> getAllStaffBuilding(){
        List<Staff_Building> staffBuildings = staffBuildingRepository.findAll();
        return staffBuildings.stream().map(staffBuildingMapper::toStaffBuildingResponse).collect(Collectors.toList());
    }

    public List<StaffBuildingResponse> getAllStaffBuildingbyBuilding(String building){
        List<Staff_Building> staffBuildings = staffBuildingRepository.findAllStaffBuildingbyBuilding(building);
        return staffBuildings.stream().map(staffBuildingMapper::toStaffBuildingResponse).collect(Collectors.toList());
    }

    public void deleteStaffBuilding(String id){
        staffBuildingRepository.deleteById(id);
    }
}
