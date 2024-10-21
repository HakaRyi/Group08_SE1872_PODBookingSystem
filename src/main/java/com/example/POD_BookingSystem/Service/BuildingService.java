package com.example.POD_BookingSystem.Service;

import com.example.POD_BookingSystem.DTO.Request.Building.CreateBuildingRequest;
import com.example.POD_BookingSystem.DTO.Request.Building.UpdateBuildingRequest;
import com.example.POD_BookingSystem.DTO.Response.BuildingResponse;
import com.example.POD_BookingSystem.Entity.EBuilding.Building;
import com.example.POD_BookingSystem.Exception.AppException;
import com.example.POD_BookingSystem.Exception.ErrorCode;
import com.example.POD_BookingSystem.Mapper.BuildingMapper;
import com.example.POD_BookingSystem.Repository.BuildingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BuildingService {
    @Autowired
    BuildingRepository buildingRepository;
    @Autowired
    BuildingMapper buildingMapper;

    // Tao Ra 1 BUILDING MOI
    public BuildingResponse createBuilding (CreateBuildingRequest request){
        String building_name = request.getName();
        if(buildingRepository.existsByName(building_name)) throw new RuntimeException("Building is existed");

        Building building = Building.builder()
                .building_id(GenerateId())
                .name(request.getName())
                .description(request.getDescription())
                .address(request.getAddress())
                .location(request.getLocation())
                .build();
        buildingRepository.save(building);
        return buildingMapper.toBuildingResponse(building);
    }

    // TAO ID 1 cach tu dong
    private String GenerateId(){
        String id = buildingRepository.findLastId();
        if(!(id == null)){
            int number = Integer.parseInt(id.substring(3))+1;
            return String.format("BD-%02d", number);
        }
        return "BD-01";
    }

    //Get All Building
    public List<BuildingResponse> getAllBuildings(){
        List<Building> buildings = buildingRepository.findAll();
        return  buildings.stream().map(buildingMapper::toBuildingResponse).collect(Collectors.toList());
    }

    //Get Building By Name
    public List<BuildingResponse> getBuildings(String name){
        List<Building> buildings = buildingRepository.findAllBuildingByName(name);
        return  buildings.stream().map(buildingMapper::toBuildingResponse).collect(Collectors.toList());
    }

    //Update Building
    public BuildingResponse updateBuilding(String id, UpdateBuildingRequest request){
        Building building = buildingRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.ID_NOT_FOUND));
        buildingMapper.updateBuilding(building, request);
        buildingRepository.save(building);
        return buildingMapper.toBuildingResponse(building);
    }

    //Get Building By Location
    public List<BuildingResponse> getBuildingsByLocation(String name){
        List<Building> buildings = buildingRepository.findAllByLocation(name);
        return  buildings.stream().map(buildingMapper::toBuildingResponse).collect(Collectors.toList());
    }

    //Delete Building
    public void deleteBuilding(String id){
        buildingRepository.deleteById(id);
    }
}
