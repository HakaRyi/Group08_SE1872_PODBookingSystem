package com.example.POD_BookingSystem.Mapper.MBooking;

import com.example.POD_BookingSystem.DTO.Response.BookingResponse;
import com.example.POD_BookingSystem.Entity.EBooking.Booking;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.4 (Oracle Corporation)"
)
@Component
public class BookingMapperImpl implements BookingMapper {

    @Override
    public BookingResponse toBookingResponse(Booking booking) {
        if ( booking == null ) {
            return null;
        }

        BookingResponse.BookingResponseBuilder bookingResponse = BookingResponse.builder();

        bookingResponse.booking_id( booking.getBooking_id() );
        bookingResponse.status( booking.getStatus() );
        bookingResponse.booking_date( booking.getBooking_date() );
        bookingResponse.total( booking.getTotal() );

        return bookingResponse.build();
    }
}
