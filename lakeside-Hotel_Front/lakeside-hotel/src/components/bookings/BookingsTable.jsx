import { parseISO } from 'date-fns'
import React, { useEffect, useState } from 'react'
import DateSlider from "../common/DateSlider"


const BookingsTable = ({ bookingInfo, handleBookingCancellation }) => {
  const [filteredBookings, setFilteredBookings] = useState(bookingInfo)

  const filterBookings = (startDate, endDate) => {
    let filtered = bookingInfo
    if (startDate && endDate) {
      filtered = bookingInfo.filter((booking) => {
        const bookingStartDate = parseISO(booking.chekInDate)
        const bookingEndDate = parseISO(booking.chekOutDate)
        return bookingStartDate >= startDate && bookingEndDate <= endDate && bookingEndDate > startDate

      })

    }
    setFilteredBookings(filtered)
  }

  useEffect(() => {
    setFilteredBookings(bookingInfo)

  }, [bookingInfo])

  return (

    <section className='p-4'>
      <DateSlider onDateChange={filterBookings} onFilterChange={filterBookings} />
      <table className="table table-bordered table-hover shadow">
        <thead>
          <tr>
            <th>S/N</th>
            <th>Booking Id</th>
            <th>Room Id</th>
            <th>Room Type</th>
            <th>Check-In Date</th>
            <th>Check-Out Date</th>
            <th>Guest Name</th>
            <th>Guest Email</th>
            <th>Adults</th>
            <th>Cheildren</th>
            <th>Total Guests</th>
            <th>Cinfirmation Code</th>
            <th colSpan={2}>Actions</th>
          </tr>
        </thead>
        <tbody className='text-center'>
          {filteredBookings.map((booking, index) => (
            <tr key={booking.id}>
              <td>{index + 1}</td>
              <td>{booking.id}</td>
              <td>{booking.room.id}</td>
              <td>{booking.room.roomType}</td>
              <td>{booking.chekInDate}</td>
              <td>{booking.chekOutDate}</td>
              <td>{booking.guestName}</td>
              <td>{booking.guesEmail}</td>
              <td>{booking.numOfAdults}</td>
              <td>{booking.numOfChilden}</td>
              <td>{booking.totalNumofGuests}</td>
              <td>{booking.bookingConfirmationCode}</td>
              <td>
                <button
                  className='btn bt-danger btn-sm'
                  onClick={() => handleBookingCancellation(booking.id)}>
                  Cancel
                </button>
              </td>
            </tr>

          ))}
        </tbody>
      </table>
      {filterBookings.length === 0 && <p>No booking found for selected Dates</p>}
    </section>
  )
}

export default BookingsTable