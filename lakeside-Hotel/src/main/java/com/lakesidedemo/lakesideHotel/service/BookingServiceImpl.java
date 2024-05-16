package com.lakesidedemo.lakesideHotel.service;

import com.lakesidedemo.lakesideHotel.exception.InvalidBookingRequestException;
import com.lakesidedemo.lakesideHotel.model.BookedRoom;
import com.lakesidedemo.lakesideHotel.model.Room;
import com.lakesidedemo.lakesideHotel.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements IBookingService {
    private final BookingRepository bookingRepository;
    private final IRoomService roomService;

    @Override
    public List<BookedRoom> getAllBookings() {
        return bookingRepository.findAll();
    }

    @Override
    public void cancelBooking(Long bookingId) {
        bookingRepository.deleteById(bookingId);
    }

    @Override
    public String saveBooking(Long roomId, BookedRoom bookingRequest) {
        if (bookingRequest.getCheckOutDate().isBefore(bookingRequest.getCheckInDate())) {
            throw new InvalidBookingRequestException("Check-in date must come before check-out date");
        }
        Room room = roomService.getRoomById(roomId).get();
//                orElseThrow(() -> new RuntimeException("Room not found"));
        List<BookedRoom> existingBookings = room.getBookings();
        boolean roomIsAvailable = roomIsAvailable(bookingRequest, existingBookings);
        if (roomIsAvailable) {
            room.addBooking(bookingRequest);
            bookingRepository.save(bookingRequest);
        }else {
            throw new InvalidBookingRequestException("Sorry, the room is not available for the selected dates; ");
        }
        return bookingRequest.getBookingConfirmationCode();
    }



    @Override
    public BookedRoom findByBookingConfirmationCode(String confirmationCode) {
        return bookingRepository.findByBookingConfirmationCode(confirmationCode);
    }

    @Override
    public List<BookedRoom> findByRoomId(Long roomId) {
        return bookingRepository.findByRoomId(roomId);
    }

    @Override
    public List<BookedRoom> getAllBookingsByRoomId(Long roomId) {
        return null;
    }


    private boolean roomIsAvailable(BookedRoom bookingRequest, List<BookedRoom> existingBookings) {
        return existingBookings.stream()
                .noneMatch(existingBooking ->
                        (bookingRequest.getCheckInDate().isBefore(existingBooking.getCheckOutDate()) ||
                                bookingRequest.getCheckOutDate().isAfter(existingBooking.getCheckInDate())));
    }
}
