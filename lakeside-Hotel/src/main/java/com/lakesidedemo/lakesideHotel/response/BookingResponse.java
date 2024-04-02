package com.lakesidedemo.lakesideHotel.response;

import com.lakesidedemo.lakesideHotel.model.Room;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingResponse {
    private Long bookingId;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private String guestFullName;
    private String guestEmail;
    private int NumOfAdults;
    private int NumOfChildren;
    private int totalNumOfGuest;

    private String bookingConfirmationCode;
    private RoomResponse room;

    public BookingResponse(String bookingConfirmationCode) {
        this.bookingConfirmationCode = bookingConfirmationCode;
    }

    public BookingResponse(Long bookingId, LocalDate checkInDate, LocalDate checkOutDate) {
        this.bookingId = bookingId;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
    }

    public BookingResponse(Long bookingId, LocalDate checkInDate, LocalDate checkOutDate, String bookingConfirmationCode) {
    }
}
