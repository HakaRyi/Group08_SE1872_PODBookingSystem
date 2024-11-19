package com.example.POD_BookingSystem.Service;

import com.example.POD_BookingSystem.DTO.Request.Room.AddServiceRequest;
import com.example.POD_BookingSystem.DTO.Request.Room.CreateRoomRequest;
import com.example.POD_BookingSystem.DTO.Request.Room.UpdateRoomRequest;
import com.example.POD_BookingSystem.DTO.Response.RoomResponse;
import com.example.POD_BookingSystem.DTO.Response.ServiceResponse;
import com.example.POD_BookingSystem.Entity.Building;
import com.example.POD_BookingSystem.Entity.ERoom.Room;
import com.example.POD_BookingSystem.Entity.ERoom.RoomServiceId;
import com.example.POD_BookingSystem.Entity.ERoom.Room_Type;
import com.example.POD_BookingSystem.Exception.AppException;
import com.example.POD_BookingSystem.Exception.ErrorCode;
import com.example.POD_BookingSystem.Mapper.RoomMapper;
import com.example.POD_BookingSystem.Mapper.ServiceMapper;
import com.example.POD_BookingSystem.Repository.BuildingRepository;
import com.example.POD_BookingSystem.Repository.ReRoom.RoomRepository;
import com.example.POD_BookingSystem.Repository.ReRoom.RoomTypeRepository;
import com.example.POD_BookingSystem.Repository.RoomServiceRepository;
import com.example.POD_BookingSystem.Repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RoomService {
    @Autowired
    RoomRepository roomRepository;
    @Autowired
    BuildingRepository buildingRepository;
    @Autowired
    RoomTypeRepository roomTypeRepository;
    @Autowired
    RoomMapper roomMapper;
    @Autowired
    ServiceRepository serviceRepository;
    @Autowired
    RoomServiceRepository roomServiceRepository;
    @Autowired
    ServiceMapper serviceMapper;

    // Tao Ra 1 Room MOI
    public RoomResponse createRoom (CreateRoomRequest request){
        Building building = buildingRepository.findByName(request.getBuilding_name());
        if(building == null) throw new AppException(ErrorCode.NAME_NOT_FOUND);

        Room_Type roomType = roomTypeRepository.findByName(request.getType_name());
        if(roomType == null) throw new RuntimeException("Room type not found");

        Room room = Room.builder()
                .room_id(GenerateId())
                .name(request.getRoom_name())
                .availability(request.getAvailability())
                .price(request.getPrice())
                .available_Date(request.getAvailable_Date())
                .building(building)
                .capacity(request.getCapacity())
                .description(request.getDescription())
                .roomType(roomType)
                .enable(true)
                .build();

        roomRepository.save(room);
        RoomResponse result = roomMapper.toRoomResponse(room);
        result.setBuilding_name(building.getBuilding_id());
        result.setType_name(roomType.getName());
        return result;
    }

    // TAO ID 1 cach tu dong
    private String GenerateId(){
        String id = roomRepository.findLastId();
        if(!(id == null)){
            int number = Integer.parseInt(id.substring(2))+1;
            return String.format("R-%02d", number);
        }
        return "R-01";
    }

    //Get All Room
    public List<RoomResponse> getAllRooms(){
        List<Room> rooms = roomRepository.findAll();
        List<Room> result = new ArrayList<>();
        for (Room room : rooms){
            if(room.getEnable() && room.getBuilding().getEnable()){
                result.add(room);
            }
        }
        return result.stream().map(roomMapper::toRoomResponse).collect(Collectors.toList());
    }

    //Get Room By Name
    public List<RoomResponse> getRoom(String name){
        List<Room> rooms = roomRepository.findAllRoomByName(name);
        List<Room> result = new ArrayList<>();
        for (Room room : rooms) {
            if (room.getEnable()) {
                result.add(room);
            }
        }
        return  result.stream().map(roomMapper::toRoomResponse).collect(Collectors.toList());
    }
    //Get Room By Building
    public List<RoomResponse> getRoomByBuilding(String building){
        List<Room> rooms = roomRepository.findRoomByBuilding(building);
        List<Room> result = new ArrayList<>();
        for (Room room : rooms) {
            if (room.getEnable()) {
                result.add(room);
            }
        }
        return  result.stream().map(roomMapper::toRoomResponse).collect(Collectors.toList());

    }
    //Get Room By RoomType
    public List<RoomResponse> getRoomByType(String type){
        List<Room> rooms = roomRepository.findAllRoomByType(type);
        List<Room> result = new ArrayList<>();
        for (Room room : rooms) {
            if (room.getEnable()) {
                result.add(room);
            }
        }
        return  result.stream().map(roomMapper::toRoomResponse).collect(Collectors.toList());

    }
    //Get Room By Status
    public List<RoomResponse> getRoomByStatus(){
        List<Room> rooms = roomRepository.findRoomByStatus();
        return  rooms.stream().map(roomMapper::toRoomResponse).collect(Collectors.toList());

    }


    //Update Room
    public RoomResponse updateRoom(String id, UpdateRoomRequest request){
        Room room = roomRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.ID_NOT_FOUND));
        roomMapper.updateRoom(room, request);
        roomRepository.save(room);
        return roomMapper.toRoomResponse(room);
    }

    //Delete Room
    public void deleteRoom(String id){
        roomRepository.deleteRoom(id);
    }

    //Get Service in Room
    public List<ServiceResponse> getServicesInRoom(String roomId){
        List<String> listServiceId = roomRepository.findServiceByRoom(roomId);
        List<ServiceResponse> serviceList = new ArrayList<>();

        for(String id : listServiceId){
            serviceList.add(serviceMapper.toServiceResponse
                    ( serviceRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.ID_NOT_FOUND))) );
        }
        return serviceList;
    }

    //ADD Service to Room
    public void addService(AddServiceRequest request, String roomId) {
        List<com.example.POD_BookingSystem.Entity.Service> services = new ArrayList<>();

        for (Map.Entry<String, Integer> entry : request.getServices().entrySet()) {
            com.example.POD_BookingSystem.Entity.Service service = serviceRepository.findByName(entry.getKey());

            if (service == null) throw new RuntimeException("Service is not exist");
            Room room = roomRepository.findById(roomId).orElseThrow(() -> new AppException(ErrorCode.ID_NOT_FOUND));

            com.example.POD_BookingSystem.Entity.ERoom.RoomService roomService =
                    roomServiceRepository.findByRoomAndService(roomId, service.getService_id());
            if (roomService != null) {
                // Nếu đã tồn tại, cập nhật quantity
                roomService.setQuantity(roomService.getQuantity() + entry.getValue());
                roomServiceRepository.save(roomService);
            } else {
                // Nếu chưa có, tạo mới
                com.example.POD_BookingSystem.Entity.ERoom.RoomService newRoomService =
                        com.example.POD_BookingSystem.Entity.ERoom.RoomService.builder()
                                .id(RoomServiceId.builder().serviceId(service.getService_id()).roomId(room.getRoom_id()).build())
                                .room(room)
                                .service(service)
                                .quantity(entry.getValue())
                                .build();

                roomServiceRepository.save(newRoomService);
            }

            serviceRepository.save(service);
            roomRepository.save(room);
        }

    }
    private String GenerateRoomServiceId() {
        String id = roomServiceRepository.findLastId();
        if (!(id == null)) {
            int number = Integer.parseInt(id.substring(3)) + 1;
            return String.format("RS-%02d", number);
        }
        return "RS-01";
    }
}
