package com.lakesidedemo.lakesideHotel.controller;

import com.lakesidedemo.lakesideHotel.exception.ResourceNotFoundException;
import com.lakesidedemo.lakesideHotel.model.BookedRoom;
import com.lakesidedemo.lakesideHotel.response.BookingResponse;
import com.lakesidedemo.lakesideHotel.service.IBookedroomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/bookings")
public class BookingController {
    private final IBookedroomService bookingService;

    @RequestMapping("all-bookings")
    public ResponseEntity<List<BookingResponse>> getAllBookings(){
        List<BookedRoom> bookings = bookingService.getAllBookings();
        List<BookingResponse> bookingResponses = new ArrayList<>();
        for(BookedRoom room:bookings){
            BookingResponse bookingResponse = getBookingResponse(booking);
            bookingResponses.add(bookingResponse);
        }
        return ResponseEntity.ok(bookingResponses);
    }

    @GetMapping("/confirmation/{confirmationCode}")
    public ResponseEntity<?> getBookingByConfirmationCode(@PathVariable String confirmationCode){
        try{
            BookedRoom booking = bookingService.findByBookingConfirmationCode(confirmationCode);
            BookingResponse bookingResponse = getBookingResponse(booking);
            return ResponseEntity.ok(bookingResponse);
        }catch(ResourceNotFoundException ex){
             return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());

        }
    }
    public ResponseEntity<?> saveBooking(@PathVariable Long roomId,
                                         @RequestBody BookedRoom bookingRequest){

        try{
            String confirmationCode = bookingService.saveBooking(roomId, bookingRequest);
            return ResponseEntity.ok(
                    "Room Booked Successfully, Your booking confirmation code is :"+ confirmationCode);

        }catch(InvalidBookingRequestException e){
            return ResponseEntity.badRequest().body(e.getMessage());

        }

    }

}
