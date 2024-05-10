package com.lakesidedemo.lakesideHotel.repository;

import com.lakesidedemo.lakesideHotel.model.BookedRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<BookedRoom> {


    BookedRoom findByBookingConfirationCode(String confirmationCode);

    List<BookedRoom> findByRoomId(Long roomId);



}
