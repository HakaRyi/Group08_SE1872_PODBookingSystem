package com.example.POD_BookingSystem.Mapper;

import com.example.POD_BookingSystem.DTO.Request.User.StaffBuildingRequest;
import com.example.POD_BookingSystem.DTO.Response.StaffBuildingResponse;
import com.example.POD_BookingSystem.Entity.Building;
import com.example.POD_BookingSystem.Entity.Staff_Building;
import com.example.POD_BookingSystem.Entity.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.4 (Oracle Corporation)"
)
@Component
public class StaffBuildingMapperImpl implements StaffBuildingMapper {

    @Override
    public Staff_Building toStaffBuilding(StaffBuildingRequest request) {
        if ( request == null ) {
            return null;
        }

        Staff_Building.Staff_BuildingBuilder staff_Building = Staff_Building.builder();

        staff_Building.start_date( request.getStart_date() );
        staff_Building.end_date( request.getEnd_date() );

        return staff_Building.build();
    }

    @Override
    public StaffBuildingResponse toStaffBuildingResponse(Staff_Building staffBuilding) {
        if ( staffBuilding == null ) {
            return null;
        }

        StaffBuildingResponse.StaffBuildingResponseBuilder staffBuildingResponse = StaffBuildingResponse.builder();

        staffBuildingResponse.building_id( staffBuildingBuildingBuilding_id( staffBuilding ) );
        staffBuildingResponse.userid_id( staffBuildingUserUserid_id( staffBuilding ) );
        staffBuildingResponse.staff_building_id( staffBuilding.getStaff_building_id() );
        staffBuildingResponse.start_date( staffBuilding.getStart_date() );
        staffBuildingResponse.end_date( staffBuilding.getEnd_date() );

        return staffBuildingResponse.build();
    }

    private String staffBuildingBuildingBuilding_id(Staff_Building staff_Building) {
        if ( staff_Building == null ) {
            return null;
        }
        Building building = staff_Building.getBuilding();
        if ( building == null ) {
            return null;
        }
        String building_id = building.getBuilding_id();
        if ( building_id == null ) {
            return null;
        }
        return building_id;
    }

    private String staffBuildingUserUserid_id(Staff_Building staff_Building) {
        if ( staff_Building == null ) {
            return null;
        }
        User user = staff_Building.getUser();
        if ( user == null ) {
            return null;
        }
        String userid_id = user.getUserid_id();
        if ( userid_id == null ) {
            return null;
        }
        return userid_id;
    }
}
