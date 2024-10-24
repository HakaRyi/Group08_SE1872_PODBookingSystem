package com.example.POD_BookingSystem.Service;

import com.example.POD_BookingSystem.DTO.Request.Booking.CreateBookingDetailRequest;
import com.example.POD_BookingSystem.DTO.Request.Service.AddServiceToBookingRequest;
import com.example.POD_BookingSystem.DTO.Response.*;
import com.example.POD_BookingSystem.Entity.EBooking.Booking;
import com.example.POD_BookingSystem.Entity.EBooking.BookingDetail;
import com.example.POD_BookingSystem.Entity.EBooking.BookingServiceId;
import com.example.POD_BookingSystem.Entity.EBooking.Booking_service;
import com.example.POD_BookingSystem.Entity.ERoom.Room;
import com.example.POD_BookingSystem.Entity.ERoom.RoomService;
import com.example.POD_BookingSystem.Entity.ERoom.RoomSlot;
import com.example.POD_BookingSystem.Entity.Slot;
import com.example.POD_BookingSystem.Exception.AppException;
import com.example.POD_BookingSystem.Exception.ErrorCode;
import com.example.POD_BookingSystem.Mapper.MBooking.BookingMapper;
import com.example.POD_BookingSystem.Mapper.ServiceMapper;
import com.example.POD_BookingSystem.Mapper.SlotMapper;
import com.example.POD_BookingSystem.Repository.ReBooking.BookingDetailRepository;
import com.example.POD_BookingSystem.Repository.ReBooking.BookingRepository;
import com.example.POD_BookingSystem.Repository.ReBooking.BookingServiceRepository;
import com.example.POD_BookingSystem.Repository.ReRoom.RoomRepository;
import com.example.POD_BookingSystem.Repository.ReRoom.RoomSlotRepository;
import com.example.POD_BookingSystem.Repository.ServiceRepository;
import com.example.POD_BookingSystem.Repository.SlotRepository;
import com.example.POD_BookingSystem.Repository.UserRepository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Slf4j
@Service
public class BookingService {
    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    BookingMapper bookingMapper;

    @Autowired
    BookingDetailRepository bookingDetailRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoomRepository roomRepository;

    @Autowired
    ServiceRepository serviceRepository;

    @Autowired
    RoomSlotRepository roomSlotRepository;

    @Autowired
    SlotRepository slotRepository;

    @Autowired
    BookingServiceRepository bookingServiceRepository;

    @Autowired
    ServiceMapper serviceMapper;

    @Autowired
    SlotMapper slotMapper;

    @Autowired
    MailService mailService;

    //GET BOOKING BY USERNAME
    public List<BookingResponse> getBookingByUsername(String username){
        String userId = userRepository.findByUsername(username).
                orElseThrow(() -> new RuntimeException("User does not Exist")).getUserid_id();
        List<Booking> listBooking  = bookingRepository.getBookingByUser(userId);
        List<BookingResponse> result = new ArrayList<>();
        if(listBooking != null){
            for(Booking booking : listBooking){
                result.add(bookingMapper.toBookingResponse(booking));
            }
        }else{
            throw new RuntimeException("You have not book any office");
        }
        return result;
    }

    //ADD MORE SERVICE
    public BookingDetailResponse addServiceToBooking (AddServiceToBookingRequest request, String bookingId, String roomName){

        String bookingVersion = GenerateDetailVesion(bookingId);

        Room room = roomRepository.findByName(roomName);
        if(room == null) throw new AppException(ErrorCode.NAME_NOT_FOUND);

        Booking booking = bookingRepository.findById(bookingId).orElseThrow(() -> new AppException(ErrorCode.ID_NOT_FOUND));
        double versionPrice = 0;

        for(Map.Entry<String,Integer> entry : request.getServices().entrySet()) {
            com.example.POD_BookingSystem.Entity.Service bookedService = serviceRepository.findByName(entry.getKey());
            if(bookedService == null) throw new AppException(ErrorCode.NAME_NOT_FOUND);

            BookingDetail serviceBookingDetail = BookingDetail.builder()
                    .booking_detail_id(GenerateDetailId())
                    .room(room)
                    .booking_type("SERVICE")
                    .service_id(bookedService.getService_id())
                    .quantity(entry.getValue())
                    .total_price(bookedService.getPrice() * entry.getValue())
                    .timestamp(LocalDateTime.now())
                    .status("CONFIRM")
                    .bookingVersion(bookingVersion)
                    .booking(booking)
                    .build();
            versionPrice += serviceBookingDetail.getTotal_price();
            bookingDetailRepository.save(serviceBookingDetail);
        }
        booking.setBookedService(request.getServices());
        booking.setTotal(booking.getTotal()+versionPrice);
        bookingRepository.save(booking);

        return BookingDetailResponse.builder()
                .roomName(room.getName())
                .bookingVersion(bookingVersion)
                .service(booking.getBookedService())
                .total_price(versionPrice)
                .build();
    }

    //GET BOOKING INFORMATION
    public BookingInformationResponse getBookingInformation(String bookingId){
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(() -> new AppException(ErrorCode.ID_NOT_FOUND));
        BookingInformationResponse bookingInformationResponse = new BookingInformationResponse();

        List<BookingDetail> listBookingDetail = booking.getBookingDetails();

        Set<Room> roomSet = new HashSet<>();

        for (BookingDetail bookingDetail : listBookingDetail){
            roomSet.add(bookingDetail.getRoom());
        }
        List<RoomInformationResponse> roomInformationResponses = new ArrayList<>();
        List<ServiceResponse> services = new ArrayList<>();

        for(Room room : roomSet){
            List<com.example.POD_BookingSystem.Entity.Service> listServices = new ArrayList<>();
            for(String serviceId : serviceRepository.getServiceByRoom(bookingId, room.getRoom_id())){
                listServices.add(serviceRepository.findById(serviceId).orElseThrow(() -> new AppException(ErrorCode.ID_NOT_FOUND)));
            }

            List<Slot> listSlots = new ArrayList<>();
            for(String slotId : slotRepository.getSlotsInRoom(bookingId,room.getRoom_id())){
                listSlots.add(slotRepository.findById(slotId).orElseThrow(() -> new AppException(ErrorCode.ID_NOT_FOUND)));
            }

            roomInformationResponses.add(RoomInformationResponse.builder()
                            .roomName(room.getName())
                            .amount(bookingDetailRepository.getRoomTotalAmount(room.getRoom_id()))
                            .endTime(bookingDetailRepository.getRoomEndTime(room.getRoom_id()))
                            .services(listServices.stream().map(service -> serviceMapper.toServiceResponse(service)).toList())
                            .startTime(bookingDetailRepository.getRoomStartTime(room.getRoom_id()))
                            .slots(listSlots.stream().map(slot -> slotMapper.toSlotResponse(slot)).toList())
                            .build());
        }
        bookingInformationResponse.setRoomInfo(roomInformationResponses);
        bookingInformationResponse.setAmount(booking.getTotal());
        bookingInformationResponse.setStatus(booking.getStatus());

        return bookingInformationResponse;
    }

//     CREATE BOOKING
    public Booking createBooking (String userName){

        Booking booking = Booking.builder()
                .booking_id(GenerateId())
                .user_id(userRepository.findByUsername(userName).
                        orElseThrow(() -> new RuntimeException("User does not Exist")).getUserid_id())
                .booking_date(LocalDate.now())
                .status("PENDING")
                .build();
        bookingRepository.save(booking);
        return booking;
    }

    // CREATE BOOKING DETAIL
    public BookingDetailResponse createBookingDetail(String bookingId, String roomName, CreateBookingDetailRequest request) {
        Room room = roomRepository.findByName(roomName);
        if (room == null) throw new RuntimeException("Room does not Exist");
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(() -> new AppException(ErrorCode.ID_NOT_FOUND));

        String version = GenerateDetailVesion(bookingId);
        double versionPrice = 0;

        //Lay Thoi Gian Thue Phong

        List<Slot> slots = new ArrayList<>();

        int numberOfSlot = 0;
        for(Map.Entry<String, List<LocalDate>> entry : request.getSlots().entrySet()){
            for(LocalDate date : entry.getValue()) {
                Slot slot = slotRepository.findById(entry.getKey()).orElseThrow(() -> new AppException(ErrorCode.ID_NOT_FOUND));
                slots.add(slot);
                numberOfSlot += 1;
            }
        }
        long timeBooking = 0;
        double bookingPrice = 0;
        if(slots.getFirst().getSlot_id().equals("Day")){
            timeBooking = ChronoUnit.DAYS.between(request.getStart_time(), request.getEnd_time() );
            if(timeBooking == 0){
                bookingPrice = ((room.getPrice()*12)* 1) - ((room.getPrice()*12)* 10 / 100);
            }else{
                bookingPrice = ((room.getPrice()*12)* (timeBooking+1)) - ((room.getPrice()*12)* 10 / 100);
            }

        } else if (slots.getFirst().getSlot_id().equals("Month")) {
            timeBooking = ChronoUnit.DAYS.between(request.getStart_time(), request.getEnd_time())/30;
            bookingPrice = ((room.getPrice()*12)* timeBooking) - ((room.getPrice()*12)* timeBooking * 10 / 100);
        } else {
            timeBooking = numberOfSlot;
            bookingPrice = room.getPrice()*timeBooking;
        }

        versionPrice += bookingPrice;

        //Lay Slot Thue Cho Booking Detail
        List<String> slotDescription = new ArrayList<>();
        for(Slot slot : slots){
            slotDescription.add(slot.getDescription());
        }

        List<BookingDetail> bookingDetails = new ArrayList<>();
        int quantity = 1;

        double bookingTotalPrice = booking.getTotal();

        BookingDetail roomBookingDetail = BookingDetail.builder()
                .booking_detail_id(GenerateDetailId())
                .room(room)
                .booking_type("ROOM")
                .quantity(quantity)
                .total_price(bookingPrice)
                .timestamp(LocalDateTime.now())
                .status("CONFIRM")
                .start_time(request.getStart_time())
                .end_time(request.getEnd_time())
                .bookingVersion(version)
                .booking(booking)
                .build();

        bookingDetails.add(roomBookingDetail);
        bookingDetailRepository.save(roomBookingDetail);

        Map<String, Integer> service = new HashMap<>();

        // Tao Booking Detail cho List Service
        int number = Integer.parseInt(GenerateDetailId().substring(3));
        for(Map.Entry<String, Integer> entry : request.getService().entrySet()){
            String serviceName = entry.getKey(); // Lấy service name
            Integer Quantity = entry.getValue();
            String id = String.format("BD-%02d", number);

            com.example.POD_BookingSystem.Entity.Service bookedService = serviceRepository.findByName(serviceName);
            if(bookedService == null) throw  new RuntimeException("Service does not exist");
            BookingDetail serviceBookingDetail = BookingDetail.builder()
                    .booking_detail_id(id)
                    .room(room)
                    .booking_type("SERVICE")
                    .service_id(bookedService.getService_id())
                    .quantity(Quantity)
                    .total_price(bookedService.getPrice() * Quantity)
                    .timestamp(LocalDateTime.now())
                    .status("CONFIRM")
                    .bookingVersion(version)
                    .booking(booking)
                    .build();

            versionPrice += (bookedService.getPrice() * Quantity);
            bookingDetails.add(serviceBookingDetail);
            bookingDetailRepository.save(serviceBookingDetail);
            service.put(bookedService.getService_id(),Quantity);
        }
        bookingTotalPrice += versionPrice;
        booking.setTotal(bookingTotalPrice);
        booking.setBookingDate(request.getSlots());
        booking.setBookingDetails(bookingDetails);
        booking.setBookedService(request.getService());

        bookingRepository.save(booking);

        return BookingDetailResponse.builder()
                .roomName(room.getName())
                .bookingVersion(version)
                .slotDescription(slotDescription)
                .service(booking.getBookedService())
                .end_time(request.getEnd_time())
                .start_time(request.getStart_time())
                .total_price(versionPrice)
                .build();
    }

    public void confirmBooking(String bookingId){
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(() -> new AppException(ErrorCode.ID_NOT_FOUND));
        String bookingVersion = bookingDetailRepository.findLastVersion(bookingId);

        Map<String, List<LocalDate>> bookingSlot = booking.getBookingDate();

        Room room = bookingDetailRepository.findDetailByVersion(bookingVersion).getFirst().getRoom();



        List<RoomSlot> roomSlotList = new ArrayList<>();
        int numberOfSlot = 0;
        if (bookingSlot != null && !bookingSlot.isEmpty()) {
            for (Map.Entry<String, List<LocalDate>> entry : bookingSlot.entrySet()) {
                    for (LocalDate date : entry.getValue()) {
                        RoomSlot roomSlot = RoomSlot.builder()
                                .room(room)
                                .uId(GenerateRoomSlotId())
                                .booking(booking)
                                .slot(slotRepository.findById(entry.getKey())
                                        .orElseThrow(() -> new AppException(ErrorCode.ID_NOT_FOUND)))
                                .booking_date(date)
                                .build();

                        roomSlotList.add(roomSlot);
                    }
                }
            }

        for(RoomSlot roomSlot : roomSlotList){
            roomSlotRepository.save(roomSlot);
        }

        //Kiem Tra Service Co san
        List<RoomService> roomServices = room.getRoomServices();
        List<String> roomBookedService = new ArrayList<>();

        if(booking.getBookedService()!=null) {
            for (Map.Entry<String, Integer> entry : booking.getBookedService().entrySet()) {
                    log.info(entry.getValue().toString());
                    int quantity = 0;
                    //Tao Id cho BookingService
                    BookingServiceId serviceId = new BookingServiceId();
                    serviceId.setBooking_id(bookingId);
                    serviceId.setService_id(serviceRepository.findByName(entry.getKey()).getService_id());

                    Booking_service existService = bookingServiceRepository.findExistService
                            (serviceRepository.findByName(entry.getKey()).getService_id(),bookingId);
                    if(existService!=null)
                {
                    quantity = existService.getQuantity() + entry.getValue();
                } else {
                        quantity = entry.getValue();
                    }
                    Booking_service bookingService = Booking_service.builder()
                            .id(serviceId)
                            .booking(booking) // đối tượng Booking
                            .service(serviceRepository.findByName(entry.getKey())) // đối tượng Service
                            .quantity(quantity)
                            .status("INTACT")
                            .build();

                for (RoomService roomService : roomServices) {
                    if(roomService.getService().getService_id().equals(bookingService.getService().getService_id())){
                        bookingService.setQuantity(bookingService.getQuantity() + roomService.getQuantity());
                    }
                }

                roomBookedService.add(bookingService.getService().getService_id());
                bookingServiceRepository.save(bookingService);
            }
        }else {
            throw new RuntimeException("Service is not exist");
        }

       for(RoomService roomService : roomServices){
           if(!roomBookedService.contains(roomService.getService().getService_id())){

               Booking_service service = Booking_service.builder()
                       .id(BookingServiceId.builder()
                               .booking_id(bookingId)
                               .service_id(roomService.getService().getService_id())
                               .build())
                       .service(roomService.getService())
                       .quantity(roomService.getQuantity())
                       .status("INTACT")
                       .booking(booking)
                       .build();


               bookingServiceRepository.save(service);
           }
       }



        booking.setStatus("CONFIRM");
        booking.setBookingDate(null);
//        booking.setBookingServices(null);

        //SEND EMAIL
        if(room.getAvailability().equals("AVAILABLE")) {
            String userName = userRepository.findById(booking.getUser_id()).
                    orElseThrow(() -> new AppException(ErrorCode.ID_NOT_FOUND)).getName();
            String address = room.getBuilding().getAddress();
            mailService.sendMail("khanghxse180105@fpt.edu.vn", "XÁC NHẬN ĐẶT PHÒNG",
                    userName, room.getName(), booking.getBooking_date().toString(), address);

            room.setAvailability("BOOKED");
        }
        bookingRepository.resetBookingDate(bookingId);
        bookingRepository.resetBookingService(bookingId);


        roomRepository.save(room);

        bookingRepository.save(booking);

    }

    //Tao ra 1 Id tang dan dua tren Id da co
    private String GenerateId(){
        String id = bookingRepository.findLastId();
        if(!(id == null)){
            int number = Integer.parseInt(id.substring(3))+1;
            return String.format("Bo-%02d", number);
        }
        return "Bo-01";
    }

    private String GenerateDetailId(){
        String id = bookingDetailRepository.findLastId();
        if(!(id == null)){
            int number = Integer.parseInt(id.substring(3))+1;
            return String.format("BD-%02d", number);
        }
        return "BD-01";
    }

    //Tao ra 1 Version moi, tang dan dua tren version da co
    private String GenerateDetailVesion(String bookingId){
        String version = bookingDetailRepository.findLastVersion(bookingId);
        if(!(version == null)){
            int number = Integer.parseInt(version.substring(8))+1;
            return String.format("bookingV%02d", number);
        }
        return "bookingV01";
    }

    private String GenerateRoomSlotId() {
        return UUID.randomUUID().toString(); // Tạo ID ngẫu nhiên
    }
}
