package com.lakesidedemo.lakesideHotel.controller;

// Declares the package for the class, organizing the code within the project structure.

import com.lakesidedemo.lakesideHotel.exception.InvalidBookingRequestException;
import com.lakesidedemo.lakesideHotel.exception.ResourceNotFoundException;
import com.lakesidedemo.lakesideHotel.model.BookedRoom;
import com.lakesidedemo.lakesideHotel.model.Room;
import com.lakesidedemo.lakesideHotel.response.BookingResponse;
import com.lakesidedemo.lakesideHotel.response.RoomResponse;
import com.lakesidedemo.lakesideHotel.service.IBookingService;
import com.lakesidedemo.lakesideHotel.service.IRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/*
 * @author Rabin
 * */

/**
 * Controller class for handling room bookings.
 */
@RequiredArgsConstructor
// Generates a constructor with required arguments (final fields) using Lombok.

@RestController
// Marks the class as a RESTful web service controller.

@RequestMapping("/bookings")
// Maps all the request handlers in this controller to /bookings URL path.

public class BookingController {

    private final IBookingService bookingService;
    // Injects the booking service to handle booking-related operations.

    private final IRoomService roomService;
    // Injects the room service to handle room-related operations.

    /**
     * Endpoint for fetching all bookings.
     *
     * @return ResponseEntity with the list of all bookings.
     */
    @GetMapping("all-bookings")
    // Maps HTTP GET requests to /bookings/all-bookings to this method.
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<BookingResponse>> getAllBookings() {
        List<BookedRoom> bookings = bookingService.getAllBookings();
        // Retrieves all bookings from the booking service.
        List<BookingResponse> bookingResponses = new ArrayList<>();
        // Creates a list to hold booking responses.
        for (BookedRoom booking : bookings) {
            BookingResponse bookingResponse = getBookingResponse(booking);
            // Converts each BookedRoom to a BookingResponse.
            bookingResponses.add(bookingResponse);
            // Adds the BookingResponse to the list.
        }

        return ResponseEntity.ok(bookingResponses);
        // Returns the list of booking responses in the response body.
    }

    /**
     * Endpoint for fetching a booking by its confirmation code.
     *
     * @param confirmationCode The confirmation code of the booking.
     * @return ResponseEntity with the booking details or error message.
     */
    @GetMapping("/confirmation/{confirmationCode}")
    // Maps HTTP GET requests to /bookings/confirmation/{confirmationCode} to this method.
    public ResponseEntity<?> getBookingByConfirmationCode(@PathVariable String confirmationCode) {
        // Method to get booking details by confirmation code.
        try {
            BookedRoom booking = bookingService.findByBookingConfirmationCode(confirmationCode);
            // Finds the booking by confirmation code using the booking service.

            BookingResponse bookingResponse = getBookingResponse(booking);
            // Converts the BookedRoom to a BookingResponse.
            return ResponseEntity.ok(bookingResponse);
            // Returns the booking response in the response body.
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
            // Returns a 404 response if the booking is not found.
        }
    }

    /**
     * Endpoint for saving a new booking.
     *
     * @param roomId The ID of the room to be booked.
     * @param bookingRequest The booking request details.
     * @return ResponseEntity with the confirmation code or error message.
     */
    @PostMapping("/room/{roomId}/booking")
    // Maps HTTP POST requests to /bookings/room/{roomId}/booking to this method.

    public ResponseEntity<?> saveBooking(@PathVariable Long roomId, @RequestBody BookedRoom bookingRequest) {
        // Method to save a new booking.

        try {
            bookingRequest.calculateTotalNumberOfGuests();
            // Ensures the total number of guests is calculated.

            System.out.println("Received booking request: " + bookingRequest);
            // Logs the booking request payload.

            String confirmationCode = bookingService.saveBooking(roomId, bookingRequest);
            // Saves the booking using the booking service and gets the confirmation code.

            return ResponseEntity.ok("Room Booked Successfully, Your booking confirmation code is :" + confirmationCode);
            // Returns a success response with the confirmation code.
        } catch (InvalidBookingRequestException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
            // Returns a 400 response if the booking request is invalid.
        }
    }

    /**
     * Endpoint for canceling a booking.
     *
     * @param bookingId The ID of the booking to be canceled.
     * @return ResponseEntity with the cancellation status or error message.
     */
    @DeleteMapping("/booking/{bookingId}/delete")
    // Maps HTTP DELETE requests to /bookings/booking/{bookingId}/delete to this method.
    public ResponseEntity<?> cancelBooking(@PathVariable Long bookingId) {
        // Method to cancel a booking.
        try {
            bookingService.cancelBooking(bookingId);
            // Cancels the booking using the booking service.

            return ResponseEntity.ok("Booking cancelled successfully.");
            // Returns a success response if the booking is cancelled.
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            // Returns a 404 response if the booking is not found.
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error cancelling booking: " + e.getMessage());
            // Returns a 400 response for any other errors.
        }
    }

    /**
     * Helper method to convert BookedRoom to BookingResponse.
     *
     * @param booking The booked room.
     * @return BookingResponse with the booking details.
     */
    private BookingResponse getBookingResponse(BookedRoom booking) {
        Room theRoom = roomService.getRoomById(booking.getRoom().getId()).get();
        // Retrieves the room details using the room service.

        RoomResponse room = new RoomResponse(theRoom.getId(), theRoom.getRoomType(), theRoom.getRoomPrice());
        // Creates a RoomResponse with the room details.

        return new BookingResponse(booking.getBookingId(), booking.getCheckInDate(), booking.getCheckOutDate(),
                booking.getGuestFullName(), booking.getGuestEmail(), booking.getNumberOfAdults(),
                booking.getNumberOfChildren(), booking.getTotalNumOfGuest(), booking.getBookingConfirmationCode(), room);
        // Creates and returns a BookingResponse with the booking details.
    }

}
