package com.example.POD_BookingSystem.Mapper;

import com.example.POD_BookingSystem.DTO.Request.Building.CreateBuildingRequest;
import com.example.POD_BookingSystem.DTO.Request.Building.UpdateBuildingRequest;
import com.example.POD_BookingSystem.DTO.Response.BuildingResponse;
import com.example.POD_BookingSystem.Entity.Building;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.4 (Oracle Corporation)"
)
@Component
public class BuildingMapperImpl implements BuildingMapper {

    @Override
    public Building toBuilding(CreateBuildingRequest request) {
        if ( request == null ) {
            return null;
        }

        Building.BuildingBuilder building = Building.builder();

        building.name( request.getName() );
        building.address( request.getAddress() );
        building.description( request.getDescription() );
        building.location( request.getLocation() );

        return building.build();
    }

    @Override
    public BuildingResponse toBuildingResponse(Building building) {
        if ( building == null ) {
            return null;
        }

        BuildingResponse.BuildingResponseBuilder buildingResponse = BuildingResponse.builder();

        buildingResponse.building_id( building.getBuilding_id() );
        buildingResponse.name( building.getName() );
        buildingResponse.address( building.getAddress() );
        buildingResponse.description( building.getDescription() );
        buildingResponse.location( building.getLocation() );

        return buildingResponse.build();
    }

    @Override
    public void updateBuilding(Building building, UpdateBuildingRequest request) {
        if ( request == null ) {
            return;
        }

        building.setName( request.getName() );
        building.setAddress( request.getAddress() );
        building.setDescription( request.getDescription() );
        building.setLocation( request.getLocation() );
    }
}
