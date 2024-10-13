package com.example.POD_BookingSystem.Mapper;

import com.example.POD_BookingSystem.DTO.Request.Service.UpdateServiceRequest;
import com.example.POD_BookingSystem.DTO.Response.ServiceResponse;
import com.example.POD_BookingSystem.Entity.Service;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ServiceMapper {
    ServiceResponse toServiceResponse(Service service);

    @Mapping(target = "service_id", ignore = true)
    void updateService(@MappingTarget Service service, UpdateServiceRequest request);
}
