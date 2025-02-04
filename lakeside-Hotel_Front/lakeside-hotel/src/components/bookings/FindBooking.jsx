import React, { useState } from 'react';
import moment from 'moment';
import { cancelBooking, getBookingByConfirmationCode } from '../utils/ApiFunctions';

const FindBooking = () => {
    const [confirmationCode, setConfirmationCode] = useState("");
    const [error, setError] = useState(null);
    const [isLoading, setIsLoading] = useState(false);
    const [successMessage, setSuccessMessage] = useState("");
    const [bookingInfo, setBookingInfo] = useState({
        id: "",
        room: { id: "" , roomType: ""},
        bookingConfirmationCode: "",
        roomNumber: "",
        checkInDate: "",
        checkOutDate: "",
        guestFullName: "",
        guestEmail: "",
        numberOfAdults: "",
        numberOfChildren: "",
        totalNumOfGuest: ""
    });

    const [isDeleted, setIsDeleted] = useState(false);

    const clearBookingInfo = {
        id: "",
        room: { id: "", roomType: ""},
        bookingConfirmationCode: "",
        roomNumber: "",
        checkInDate: "",
        checkOutDate: "",
        guestFullName: "",
        guestEmail: "",
        numberOfAdults: "",
        numberOfChildren: "",
        totalNumOfGuest: ""
    };

    const handleInputChange = (event) => {
        setConfirmationCode(event.target.value);
    };

    const handleFormSubmit = async (event) => {
        event.preventDefault();
        setIsLoading(true);
        try {
            const data = await getBookingByConfirmationCode(confirmationCode);
            console.log("Booking data fetched:", data); // Debug log
            setBookingInfo(data);
            setError(null)
        } catch (error) {
            console.error("Error fetching booking:", error); // Debug log
            setBookingInfo(clearBookingInfo);
            if (error.response && error.response.status === 404) {
                setError(error.response.data.message);
            } else {
                setError(error.message);
            }
        } finally {
            setIsLoading(false);
        }
        setTimeout(() => setIsLoading(false), 2000)
    };

    const handleBookingCancellation = async (bookingInfo) => {
        try {
            console.log("Attempting to cancel booking with ID:", bookingInfo.id); // Debug log
            await cancelBooking(bookingInfo.id);
            setIsDeleted(true);
            setSuccessMessage("Booking has been cancelled successfully!");
            setBookingInfo(clearBookingInfo);
            setConfirmationCode("");
            setError(null)
        } catch (error) {
            console.error("Error cancelling booking:", error); // Debug log
            setError(`Error cancelling booking: ${error.message}`);
        }
        setTimeout(() => {
            setSuccessMessage("");
            setIsDeleted(false);
        }, 2000);
    };

    return (
        <>
            <div className='container mt-5 d-flex flex-column justify-content-center align-items-center'>
                <h2 className='text-center mb-4'>Find My Booking</h2>
                <form onSubmit={handleFormSubmit} className='col-md-6'>
                    <div className='input-group mb-3'>
                        <input
                            className='form-control'
                            type='text'
                            id="confirmationCode"
                            name="confirmationCode"
                            value={confirmationCode}
                            onChange={handleInputChange}
                            placeholder='Enter the booking confirmation code'
                        />
                        <button className='btn btn-hotel input-group-text' type="submit">Find Booking</button>
                    </div>
                </form>
                
                {isLoading ? (
                    <div>Finding your Booking...</div>
                ) : error ? (
                    <div className='text-danger'>Error: {error}</div>
                ) : bookingInfo.bookingConfirmationCode ? (
                    <div className='col-md-6 mt-4 mb-5'>
                        <h3>Booking Information</h3>
                        <p>Booking Confirmation Code: {bookingInfo.bookingConfirmationCode}</p>
                        <p>Room Number: {bookingInfo.room.id}</p>
                        <p>Room Type: {bookingInfo.room.roomType}</p>
                        {/* <p>Check-in Date: {bookingInfo.checkInDate}</p> */}
                        <p>
							Check-in Date:{" "}
							{moment(bookingInfo.checkInDate).subtract(1, "month").format("MMM Do, YYYY")}
						</p>
                        {/* <p>Check-Out Date: {bookingInfo.checkOutDate}</p> */}
                        <p>
							Check-out Date:{" "}
							{moment(bookingInfo.checkOutDate).subtract(1, "month").format("MMM Do, YYYY")}
						</p>
                        <p>Full Name: {bookingInfo.guestFullName}</p>
                        <p>Email Address: {bookingInfo.guestEmail}</p>
                        <p>Adults: {bookingInfo.numberOfAdults}</p>
                        <p>Children: {bookingInfo.numberOfChildren}</p>
                        <p>Total Guests: {bookingInfo.totalNumOfGuest}</p>
                        {!isDeleted && (
                            <button className='btn btn-danger' 
                            onClick={() => handleBookingCancellation(bookingInfo.id)}>
                                Cancel Booking
                            </button>
                        )}
                    </div>
                ) : (
                    <div>Find booking...</div>
                )}
                {isDeleted && (
                    <div className='alert alert-success mt-3' role='alert'>
                        {successMessage}
                    </div>
                )}
            </div>
        </>
    );
};

export default FindBooking;
