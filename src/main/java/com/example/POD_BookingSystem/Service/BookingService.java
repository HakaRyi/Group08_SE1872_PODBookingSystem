package com.example.POD_BookingSystem.Service;
import com.example.POD_BookingSystem.DTO.Request.Booking.ConfirmRequest;
import com.example.POD_BookingSystem.DTO.Request.Booking.CreateMonthBookingRequest;
import com.example.POD_BookingSystem.DTO.Request.Service.AddServiceToBookingRequest;
import com.example.POD_BookingSystem.DTO.Response.*;
import com.example.POD_BookingSystem.Entity.Building;
import com.example.POD_BookingSystem.Entity.EBooking.*;
import com.example.POD_BookingSystem.Entity.ERoom.RoomService;
import com.example.POD_BookingSystem.DTO.Request.Booking.CreateBookingDetailRequest;
import com.example.POD_BookingSystem.Entity.ERoom.Room;
import com.example.POD_BookingSystem.Entity.ERoom.RoomSlot;
import com.example.POD_BookingSystem.Entity.Slot;
import com.example.POD_BookingSystem.Entity.User;
import com.example.POD_BookingSystem.Exception.AppException;
import com.example.POD_BookingSystem.Exception.ErrorCode;
import com.example.POD_BookingSystem.Mapper.MBooking.BookingMapper;
import com.example.POD_BookingSystem.Mapper.ServiceMapper;
import com.example.POD_BookingSystem.Mapper.SlotMapper;
import com.example.POD_BookingSystem.Repository.ReBooking.BookingDetailRepository;
import com.example.POD_BookingSystem.Repository.ReBooking.BookingRepository;
import com.example.POD_BookingSystem.Repository.ReBooking.BookingServiceRepository;
import com.example.POD_BookingSystem.Repository.ReBooking.MonthBookingRepository;
import com.example.POD_BookingSystem.Repository.ReRoom.RoomRepository;
import com.example.POD_BookingSystem.Repository.ReRoom.RoomSlotRepository;
import com.example.POD_BookingSystem.Repository.ServiceRepository;
import com.example.POD_BookingSystem.Repository.SlotRepository;
import com.example.POD_BookingSystem.Repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class BookingService {
    @Autowired
    BookingRepository bookingRepository;

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
    TransactionService transactionService;

    @Autowired
    PaymentService paymentService;

    @Autowired
    SlotMapper slotMapper;

    @Autowired
    ServiceMapper serviceMapper;

    @Autowired
    BookingMapper bookingMapper;

    @Autowired
    MailService mailService;

    @Autowired
    MonthBookingRepository monthBookingRepository;

    public List<BookingResponse> getCheckin(){
        List<Booking> bookings = bookingRepository.findByStatusCheckin();
        return bookings.stream().map(booking -> {
            BookingResponse response = new BookingResponse();
            response.setBooking_id(booking.getBooking_id());
            response.setStatus(booking.getStatus());
            response.setName(booking.getUser().getName()); // Lấy user_id từ User và gán vào name
            response.setBooking_date(booking.getBooking_date());
            response.setTotal(booking.getTotal());
            return response;
        }).collect(Collectors.toList());
    }
    public List<BookingResponse> getCheckout(){
        List<Booking> bookings = bookingRepository.findByStatusCheckout();
        return bookings.stream().map(booking -> {
            BookingResponse response = new BookingResponse();
            response.setBooking_id(booking.getBooking_id());
            response.setStatus(booking.getStatus());
            response.setName(booking.getUser().getName()); // Lấy user_id từ User và gán vào name
            response.setBooking_date(booking.getBooking_date());
            response.setTotal(booking.getTotal());
            return response;
        }).collect(Collectors.toList());
    }
    public List<BookingResponse> getBookByConfirm(){
        List<Booking> bookings = bookingRepository.findByConfirm();
        return bookings.stream().map(booking -> {
            BookingResponse response = new BookingResponse();
            response.setBooking_id(booking.getBooking_id());
            response.setStatus(booking.getStatus());
            response.setName(booking.getUser().getName()); // Lấy user_id từ User và gán vào name
            response.setBooking_date(booking.getBooking_date());
            response.setTotal(booking.getTotal());
            return response;
        }).collect(Collectors.toList());
    }
    public List<BookingResponse> getAllBook(){
        List<Booking> bookings = bookingRepository.findAll();
        return bookings.stream().map(booking -> {
            BookingResponse response = new BookingResponse();
            response.setBooking_id(booking.getBooking_id());
            response.setStatus(booking.getStatus());
            response.setName(booking.getUser().getName()); // Lấy user_id từ User và gán vào name
            response.setBooking_date(booking.getBooking_date());
            response.setTotal(booking.getTotal());
            return response;
        }).collect(Collectors.toList());
    }

    //GET BOOKING BY USERNAME
    public List<BookingResponse> getBookingByUsername(String username){
        String userId = userRepository.findByUsername(username).
                orElseThrow(() -> new RuntimeException("User does not Exist")).getUserid_id();
        List<Booking> listBooking  = bookingRepository.getBookingByUser(userId);
        List<BookingResponse> result = new ArrayList<>();
        if(listBooking != null){
            for(Booking booking : listBooking){
                BookingResponse response = new BookingResponse();
                response.setBooking_id(booking.getBooking_id());
                response.setStatus(booking.getStatus());
                response.setName(booking.getUser().getName()); // Gán username vào thuộc tính name
                response.setBooking_date(booking.getBooking_date());
                response.setTotal(booking.getTotal());
                result.add(response);
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
        BookingInformationResponse bookingInformationResponse = new BookingInformationResponse();

        Booking booking = bookingRepository.findById(bookingId).orElseThrow(() -> new AppException(ErrorCode.ID_NOT_FOUND));

        List<BookingDetail> listBookingDetail = booking.getBookingDetails();

        Room room = listBookingDetail.getFirst().getRoom();
        log.info(room.getName());

        log.info("at service");
        List<com.example.POD_BookingSystem.Entity.Service> listServices = new ArrayList<>();
        for (String serviceId : serviceRepository.getServiceByRoom(bookingId, room.getRoom_id())) {
            listServices.add(serviceRepository.findById(serviceId).orElseThrow(() -> new AppException(ErrorCode.ID_NOT_FOUND)));
        }

        List<Slot> listSlots = new ArrayList<>();
        for (String slotId : slotRepository.getSlotsInRoom(bookingId, room.getRoom_id())) {
            listSlots.add(slotRepository.findById(slotId).orElseThrow(() -> new AppException(ErrorCode.ID_NOT_FOUND)));
        }

        RoomInformationResponse response = RoomInformationResponse.builder()
                .roomName(room.getName())
                .amount(bookingDetailRepository.getRoomTotalAmount(room.getRoom_id(), bookingId))
                .endTime(bookingDetailRepository.getRoomEndTime(room.getRoom_id(), bookingId))
                .services(listServices.stream().map(service -> serviceMapper.toServiceResponse(service)).toList())
                .startTime(bookingDetailRepository.getRoomStartTime(room.getRoom_id(), bookingId))
                .slots(listSlots.stream().map(slot -> slotMapper.toSlotResponse(slot)).toList())
                .build();

        bookingInformationResponse.setRoomInfo(response);
        bookingInformationResponse.setAmount(booking.getTotal());
        bookingInformationResponse.setStatus(booking.getStatus());

        log.info(bookingInformationResponse.toString());

        return bookingInformationResponse;
    }

    //     CREATE BOOKING
//    public Booking createBooking (String userName){
//        User user = userRepository.findByPhone(userName)
//                .orElseThrow(() -> new RuntimeException("User does not exist"));
//        Booking booking = Booking.builder()
//                .booking_id(GenerateId())
//                .user(user)
//                .booking_date(LocalDate.now())
//                .status("PENDING")
//                .build();
//        bookingRepository.save(booking);
//        return booking;
//    }
        public BookingResponse createBooking (String userName){
            User user = userRepository.findByUsername(userName)
                    .orElseThrow(() -> new RuntimeException("User does not exist"));
            Booking booking = Booking.builder()
                    .booking_id(GenerateId())
                    .user(user)
                    .booking_date(LocalDate.now())
                    .status("PENDING")
                    .build();
            bookingRepository.save(booking);

            return BookingResponse.builder()
                    .booking_id(booking.getBooking_id())
                    .booking_date(booking.getBooking_date())
                    .name(user.getName())
                    .status("PENDING")
                    .build();
        }

    //CANCEL PAYMENT
    public void cancelPayment(int amount, String bookingId){
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(() -> new AppException(ErrorCode.ID_NOT_FOUND));
        booking.setTotal(booking.getTotal()-amount);
        bookingDetailRepository.resetBookingDetail(bookingId);
        bookingRepository.save(booking);
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
        for(Map.Entry<String, List<String>> entry : request.getSlots().entrySet()){
            for(String date : entry.getValue()) {
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
                .status("PENDING")
                .start_time(request.getStart_time())
                .end_time(request.getEnd_time())
                .bookingVersion(version)
                .booking(booking)
                .build();

        bookingDetails.add(roomBookingDetail);
        bookingDetailRepository.save(roomBookingDetail);

        Map<String, Integer> service = new HashMap<>();

        // Tao Booking Detail cho List Service

        for(Map.Entry<String, Integer> entry : request.getService().entrySet()){
            String serviceName = entry.getKey(); // Lấy service name
            Integer Quantity = entry.getValue();


            com.example.POD_BookingSystem.Entity.Service bookedService = serviceRepository.findByName(serviceName);
            if(bookedService == null) throw  new RuntimeException("Service does not exist");
            BookingDetail serviceBookingDetail = BookingDetail.builder()
                    .booking_detail_id(GenerateDetailId())
                    .room(room)
                    .booking_type("SERVICE")
                    .service_id(bookedService.getService_id())
                    .quantity(Quantity)
                    .total_price(bookedService.getPrice() * Quantity)
                    .timestamp(LocalDateTime.now())
                    .status("PENDING")
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

    public void confirmBooking(String bookingId, ConfirmRequest request){
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(() -> new RuntimeException("Booking Not Found"));
        List<BookingDetail> bookingDetails = booking.getBookingDetails();

        Map<String, List<String>> bookingSlot = booking.getBookingDate();

        Room room = bookingDetails.getFirst().getRoom();
        MonthBooking roomBookedByMonth = monthBookingRepository.isBookingByMonth(bookingId);
        if(roomBookedByMonth != null){
            roomBookedByMonth.setStatus("CONFIRM");
            monthBookingRepository.save(roomBookedByMonth);
        }
        for(BookingDetail bookingDetail : bookingDetails){
            bookingDetail.setStatus("CONFIRM");
        }

        if (bookingSlot != null && !bookingSlot.isEmpty()) {
            List<RoomSlot> roomSlotList = new ArrayList<>();
            for (Map.Entry<String, List<String>> entry : bookingSlot.entrySet()) {
                if(entry.getKey().equals("Day") || entry.getKey().equals("Month")) {
                    for (String date : entry.getValue()) {
                        RoomSlot roomSlot = RoomSlot.builder()
                                .room(room)
                                .uId(GenerateRoomSlotId())
                                .booking(booking)
                                .slot(slotRepository.findById(entry.getKey())
                                        .orElseThrow(() -> new RuntimeException("Slot not found")))
                                .booking_date(date)
                                .build();

                        roomSlotList.add(roomSlot);
                    }
                }else {
                    for (String date : entry.getValue()) {
                        RoomSlot roomSlot = RoomSlot.builder()
                                .room(room)
                                .uId(GenerateRoomSlotId())
                                .booking(booking)
                                .slot(slotRepository.findById(entry.getKey())
                                        .orElseThrow(() -> new RuntimeException("Slot not found")))
                                .booking_date(bookingDetailRepository.getRoomStartTime(room.getRoom_id(), bookingId).toString())
                                .build();

                        roomSlotList.add(roomSlot);
                    }
                }
            }
            for (RoomSlot roomSlot : roomSlotList) {
                roomSlotRepository.save(roomSlot);
            }
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
        User user = booking.getUser();
        log.info("User EMail:" + user.getEmail());
        String userName = user.getName();
        String address = room.getBuilding().getAddress();
        mailService.sendMail(user.getEmail(), "XÁC NHẬN ĐẶT PHÒNG",
                userName, room.getName(), booking.getBooking_date().toString(), address);
            room.setAvailability("BOOKED");

        bookingRepository.resetBookingDate(bookingId);
        bookingRepository.resetBookingService(bookingId);



        roomRepository.save(room);
        LocalDate date;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
            LocalDateTime dateTime = LocalDateTime.parse(request.getBookingDate(), formatter);
            date = dateTime.toLocalDate();
            log.info(booking.getBooking_date().toString());
            if (booking.getStatus().equals("PENDING")) {
                booking.setBooking_date(date);
            }
        }catch (DateTimeParseException e){
            log.error("Failed to parse bookingDate: "+request.getBookingDate(),e);
            throw new RuntimeException("Invalid booking date format.");
        }
        if(booking.getTxnRef()==null){
            booking.setTxnRef(request.getCode());
        }
        bookingRepository.save(booking);
        paymentService.createPayment(booking);
        log.info("go to createPaymentDetail");

        paymentService.createPaymentDetail(booking);
        log.info("go to Transaction");
        transactionService.createTransaction(booking,booking.getUser());
        log.info("go to TransactionDetail");
        transactionService.createTransactionDetail(booking);
    }

    //Tao ra 1 Id tang dan dua tren Id da co
    private String GenerateId(){
        int number = bookingRepository.findAll().size()+1;
        return "Bo-" + number;
    }

    private String GenerateDetailId(){
        int number = bookingDetailRepository.findAll().size()+1;
        return "BD-" + number;
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
    //CHECKIN
    public void requestCheckin(String bookingId){
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
        if(!"CONFIRM".equals(booking.getStatus())){
            throw new RuntimeException("Request check in can only be made for booking with status 'CONFIRM'");
        }
      booking.setStatus("CHECK_IN_REQUEST");
        bookingRepository.save(booking);
    }
    public void approveCheckin(String bookingId){
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
        if(!"CHECK_IN_REQUEST".equals(booking.getStatus())){
            throw new RuntimeException("Check in can only be approved for booking with status 'CHECK_IN_REQUEST'");
        }
        booking.setStatus("CHECK_IN");
        bookingRepository.save(booking);

        List<BookingDetail> bookingDetails = bookingDetailRepository.findByBookingId(bookingId);
        for (BookingDetail detail : bookingDetails) {
            if (!"CHECK_IN".equals(detail.getStatus())) {
                detail.setStatus("CHECK_IN");
                bookingDetailRepository.save(detail);
            }
        }
        transactionService.createTransactionDetail(booking);
    }
    public void rejectCheckin(String bookingId){
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
        if(!"CHECK_IN_REQUEST".equals(booking.getStatus())){
            throw new RuntimeException("Reject check in can only be rejected for booking with status 'CHECK_IN_REQUEST'");
        }
        booking.setStatus("CONFIRM");
        bookingRepository.save(booking);
    }


    //CHECK OUT
    public void requestCheckout(String bookingId){
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
        if(!"CHECK_IN".equals(booking.getStatus())){
            throw new RuntimeException("Request check out can only be made for booking with status 'CHECK_IN'");
        }

        booking.setStatus("CHECK_OUT_REQUEST");
        bookingRepository.save(booking);
    }
    public void approveCheckout(String bookingId){
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
        if(!"CHECK_OUT_REQUEST".equals(booking.getStatus())){
            throw new RuntimeException("Check out can only be approved for booking with status 'CHECK_OUT_REQUEST'");
        }

        booking.setStatus("CHECK_OUT");
        bookingRepository.save(booking);

        List<BookingDetail> bookingDetails = bookingDetailRepository.findByBookingId(bookingId);
        for (BookingDetail detail : bookingDetails) {
            if (!"CHECK_OUT".equals(detail.getStatus())) {
                detail.setStatus("CHECK_OUT");
                bookingDetailRepository.save(detail);
            }
        }
        transactionService.createTransactionDetail(booking);
    }
    public void rejectCheckout(String bookingId){
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
        if(!"CHECK_OUT_REQUEST".equals(booking.getStatus())){
            throw new RuntimeException("Reject check out can only be rejected for booking with status 'CHECK_OUT_REQUEST'");
        }
        booking.setStatus("CHECK_IN");
        bookingRepository.save(booking);
    }
    // CREATE MONTH BOOKING DETAIL
    public MonthBookingResponse createMonthBookingDetail(CreateMonthBookingRequest request) {
        Room room = roomRepository.findByName(request.getRoomName());
        if (room == null) throw new RuntimeException("Room does not Exist");

        //CREATE NEW BOOKING
        User user = userRepository.findByPhone(request.getPhone()).
                orElseThrow(() -> new RuntimeException("User does not exist or phone is not register"));
        String id = GenerateId();
        Booking booking = Booking.builder()
                .booking_id(id)
                .user(user)
                .booking_date(LocalDate.now())
                .status("PENDING")
                .build();
        bookingRepository.save(booking);

        MonthBooking monthBooking = MonthBooking.builder()
                .booking_id(id)
                .status("PENDING")
                .booking_date(LocalDate.now())
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .user(user)
                .build();
        int numberOfMonth = request.getNumberOfMonth();

        double bookingPrice = (((room.getPrice() * 12) * 30 * numberOfMonth) - ((room.getPrice() * 12) * 30 * numberOfMonth * 18 / 100));

        var bookingId = booking.getBooking_id();
        String version = GenerateDetailVesion(bookingId);
        double versionPrice = 0;

        versionPrice += bookingPrice;

        List<BookingDetail> bookingDetails = new ArrayList<>();

        double bookingTotalPrice = booking.getTotal();

        BookingDetail roomBookingDetail = BookingDetail.builder()
                .booking_detail_id(GenerateDetailId())
                .room(room)
                .booking_type("ROOM")
                .quantity(1)
                .total_price(bookingPrice)
                .timestamp(LocalDateTime.now())
                .status("PENDING")
                .start_time(request.getStartTime())
                .end_time(request.getEndTime())
                .bookingVersion(version)
                .booking(booking)
                .build();

        bookingDetails.add(roomBookingDetail);
        bookingDetailRepository.save(roomBookingDetail);

        Map<String, Integer> service = new HashMap<>();

        // Tao Booking Detail cho List Service

        for (Map.Entry<String, Integer> entry : request.getService().entrySet()) {
            String serviceName = entry.getKey(); // Lấy service name
            Integer Quantity = entry.getValue();

            com.example.POD_BookingSystem.Entity.Service bookedService = serviceRepository.findByName(serviceName);
            if (bookedService == null) throw new RuntimeException("Service does not exist");
            BookingDetail serviceBookingDetail = BookingDetail.builder()
                    .booking_detail_id(GenerateDetailId())
                    .room(room)
                    .booking_type("SERVICE")
                    .service_id(bookedService.getService_id())
                    .quantity(Quantity)
                    .total_price(bookedService.getPrice() * Quantity)
                    .timestamp(LocalDateTime.now())
                    .status("PENDING")
                    .bookingVersion(version)
                    .booking(booking)
                    .build();

            versionPrice += (bookedService.getPrice() * Quantity);
            bookingDetails.add(serviceBookingDetail);
            bookingDetailRepository.save(serviceBookingDetail);
            service.put(bookedService.getService_id(), Quantity);
        }
        bookingTotalPrice += versionPrice;
        booking.setTotal(bookingTotalPrice);
        booking.setBookingDetails(bookingDetails);
        booking.setBookedService(request.getService());

        bookingRepository.save(booking);
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        String formattedCurrency = currencyFormat.format(versionPrice);
        monthBooking.setTotal(bookingPrice);
        monthBookingRepository.save(monthBooking);
        return MonthBookingResponse.builder()
                .total(formattedCurrency)
                .phoneNumber(request.getPhone())
                .userName(user.getName())
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .booking_date(LocalDate.now())
                .booking_id(bookingId)
                .build();
    }
}
