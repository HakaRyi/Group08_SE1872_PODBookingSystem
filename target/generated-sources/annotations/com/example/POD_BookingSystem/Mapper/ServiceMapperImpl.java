package com.example.POD_BookingSystem.Mapper;

import com.example.POD_BookingSystem.DTO.Request.Service.UpdateServiceRequest;
import com.example.POD_BookingSystem.DTO.Response.ServiceResponse;
import com.example.POD_BookingSystem.Entity.Service;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.4 (Oracle Corporation)"
)
@Component
public class ServiceMapperImpl implements ServiceMapper {

    @Override
    public ServiceResponse toServiceResponse(Service service) {
        if ( service == null ) {
            return null;
        }

        ServiceResponse.ServiceResponseBuilder serviceResponse = ServiceResponse.builder();

        serviceResponse.name( service.getName() );
        serviceResponse.description( service.getDescription() );
        serviceResponse.price( service.getPrice() );

        return serviceResponse.build();
    }

    @Override
    public void updateService(Service service, UpdateServiceRequest request) {
        if ( request == null ) {
            return;
        }

        service.setName( request.getName() );
        service.setFee( request.getFee() );
        service.setDescription( request.getDescription() );
        service.setPrice( request.getPrice() );
    }
}
