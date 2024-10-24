package com.example.POD_BookingSystem.Service;
import com.example.POD_BookingSystem.DTO.Request.Service.CreateServiceRequest;
import com.example.POD_BookingSystem.DTO.Request.Service.UpdateServiceRequest;
import com.example.POD_BookingSystem.DTO.Response.ServiceResponse;
import com.example.POD_BookingSystem.Exception.AppException;
import com.example.POD_BookingSystem.Exception.ErrorCode;
import com.example.POD_BookingSystem.Mapper.ServiceMapper;
import com.example.POD_BookingSystem.Repository.ReRoom.RoomRepository;
import com.example.POD_BookingSystem.Repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServiceService {
    @Autowired
    ServiceMapper serviceMapper;
    @Autowired
    ServiceRepository serviceRepository;
    @Autowired
    RoomRepository roomRepository;

    // Tao Ra 1 SERVICE MOI
    public ServiceResponse createService (CreateServiceRequest request){
        String service_name = request.getName();
        if(serviceRepository.existsByName(service_name)) throw new RuntimeException("Service is existed");
        com.example.POD_BookingSystem.Entity.Service service = com.example.POD_BookingSystem.Entity.Service.builder()
                .service_id(GenerateId())
                .price(request.getPrice())
                .fee(request.getFee())
                .description(request.getDescription())
                .name(request.getName())
                .build();

        serviceRepository.save(service);

        return serviceMapper.toServiceResponse(service);
    }

    // TAO ID 1 cach tu dong
    private String GenerateId(){
        String id = serviceRepository.findLastId();
        if(!(id == null)){
            int number = Integer.parseInt(id.substring(2))+1;
            return String.format("S-%02d", number);
        }
        return "S-01";
    }

    //Get All SERVICE
    public List<ServiceResponse> getAllService(){
        List<com.example.POD_BookingSystem.Entity.Service> services = serviceRepository.findAll();
        return  services.stream().map(serviceMapper::toServiceResponse).collect(Collectors.toList());
    }

    //Get Service By Name
    public List<ServiceResponse> getServices(String name){
        List<com.example.POD_BookingSystem.Entity.Service> Services = serviceRepository.findAllServiceByName(name);
        return  Services.stream().map(serviceMapper::toServiceResponse).collect(Collectors.toList());
    }

    //Update Service
    public ServiceResponse updateService(String id, UpdateServiceRequest request){
        com.example.POD_BookingSystem.Entity.Service Service = serviceRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.ID_NOT_FOUND));
        serviceMapper.updateService(Service, request);
        serviceRepository.save(Service);
        return serviceMapper.toServiceResponse(Service);
    }

    //Delete Service
    public void deleteService(String id){
        serviceRepository.deleteById(id);
    }
}
