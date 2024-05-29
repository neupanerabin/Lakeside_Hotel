import React, { useState } from 'react'

const BookingsTable = ({bookingInfo, handleBookingCancellation}) => {
    const[filterdBookings, setFilteredBookings] = useState(bookingInfo)

    const filterBookings = (startDate, endDate) =>{
        let filtered = bookingInfo
        if(startDate && endDate){
            filtered = bookingInfo.filter((booking) =>{
                const bookingStartDate = parseISO(booking.chekInDate)
                const bookingEndDate = parseISO(booking.chekOutDate)
                return bookingStartDate >= startDate && bookingEndDate <= endDate && bookingEndDate > startDate
                
            })

        }


    }

  return (
    <div>BookingsTable</div>
  )
}

export default BookingsTable