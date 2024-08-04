import React, { useEffect, useState } from 'react';
import { bookRoom, getRoomById } from '../utils/ApiFunctions'; // Corrected function name
import { useNavigate, useParams } from 'react-router';
import moment from "moment"; // Corrected import
import { Form, FormControl, Button } from 'react-bootstrap';
import BookingSummary from './BookingSummary';
import { useAuth } from "../auth/AuthProvider"

const BookingForm = () => {
    const [validated, setValidated] = useState(false);
    const [isSubmitted, setIsSubmitted] = useState(false);
    const [errorMessage, setErrorMessage] = useState("");
    const [roomPrice, setRoomPrice] = useState(0);
    
    const currentUser = localStorage.getItem("userId")

    const [booking, setBooking] = useState({
        guestFullName: "",
        guestEmail: "currentUser",
        checkInDate: "",
        checkOutDate: "",
        numberOfAdults: "",
        numberOfChildren: ""
    });

    const { roomId } = useParams();
    const navigate = useNavigate();

    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setBooking({ ...booking, [name]: value });
        setErrorMessage("");
    };

    const getRoomPriceById = async (roomId) => {
        try {
            const response = await getRoomById(roomId);
            setRoomPrice(response.roomPrice);
        } catch (error) {
            throw new Error(error);
  
        }
    };

    useEffect(() => {
        if (roomId) {
            getRoomPriceById(roomId);
        }
    }, [roomId]);
    

    const calculatePayment = () => {
        const checkInDate = moment(booking.checkInDate);
        const checkOutDate = moment(booking.checkOutDate);
        const diffInDays = checkOutDate.diff(checkInDate, 'days');
        const paymentPerDay = roomPrice ? roomPrice : 0;
        return diffInDays * paymentPerDay;
    };

    const isGuestCountValid = () => {
        const adultCount = parseInt(booking.numberOfAdults);
        const childrenCount = parseInt(booking.numberOfChildren);
        const totalCount = adultCount + childrenCount;
        return totalCount >= 1 && adultCount >= 1
    };

    const isCheckOutDateValid = () => {
        if (!moment(booking.checkOutDate).isSameOrAfter(moment(booking.checkInDate))) {
            setErrorMessage("Check-out date must be after or the same as the check-in date");
            return false;
        } else {
            setErrorMessage("");
            return true;
        }
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        const form = e.currentTarget;
        if (form.checkValidity() === false || !isGuestCountValid() || !isCheckOutDateValid()) {
            e.stopPropagation();
        } else {
            setIsSubmitted(true);
        }
        setValidated(true);
    };

    const handleFormSubmit = async () => {
        try {
            const confirmationCode = await bookRoom(roomId, booking);
            setIsSubmitted(true);
            navigate("/booking-success", { state: { message: confirmationCode } }); // Removed extra space
        } catch (error) {
            const errorMessage = error.message
            console.log(errorMessage)
            navigate("/booking-success", { state: { error: errorMessage } });
        }
    };

    return (
        <>
            <div className='container mb-5'>
                <div className='row'>
                    <div className='col-md-6'>
                        <div className='card card-body mt-4'>
                            <h4 className='card-title'>Reserved Room</h4>
                            <Form noValidate validated={validated} onSubmit={handleSubmit}>
                                <Form.Group>
                                    <Form.Label htmlFor="guestFullName" className='hotel-color'>Full Name: </Form.Label>
                                    <Form.Control
                                        required
                                        type="text"
                                        id="guestFullName"
                                        name="guestFullName"
                                        value={booking.guestFullName}
                                        placeholder="Enter your full name"
                                        onChange={handleInputChange}
                                    />
                                    <Form.Control.Feedback type="invalid">
                                        Please enter your fullname
                                    </Form.Control.Feedback>
                                </Form.Group>
                                <Form.Group>
                                    <Form.Label htmlFor="guestEmail">Email: </Form.Label>
                                    <Form.Control
                                        required
                                        type="email"
                                        id="guestEmail"
                                        name='guestEmail'
                                        value={booking.guestEmail}
                                        placeholder="Enter your email"
                                        onChange={handleInputChange}
                                    />
                                    <Form.Control.Feedback type="invalid">
                                        Please enter your email
                                    </Form.Control.Feedback>
                                </Form.Group>
                                <fieldset style={{ border: "2px" }}>
                                    <legend>   Lodging Period</legend>
                                    <div className='row'>
                                        <div className='col-6'>
                                            <Form.Label htmlFor="checkInDate">Check-In Date: </Form.Label>
                                            <Form.Control
                                                required
                                                type="date"
                                                id="checkInDate"
                                                name="checkInDate"
                                                value={booking.checkInDate}
                                                placeholder="Enter your check-in Date"
                                                onChange={handleInputChange}
                                            />
                                            <Form.Control.Feedback type="invalid">
                                                Please select a check in date
                                            </Form.Control.Feedback>
                                        </div>
                                        <div className='col-6'>
                                            <Form.Label htmlFor="checkOutDate">Check-Out Date: </Form.Label>
                                            <Form.Control
                                                required
                                                type="date"
                                                id="checkOutDate"
                                                name="checkOutDate"
                                                value={booking.checkOutDate}
                                                placeholder="Enter your check-in Date"
                                                onChange={handleInputChange}
                                            />
                                            <Form.Control.Feedback type="invalid">
                                                Please select a check Out date
                                            </Form.Control.Feedback>
                                        </div>
                                        {errorMessage && <p className='error-message text-danger'>{errorMessage}</p>}
                                    </div>
                                </fieldset>
                                <fieldset>
                                    <legend>Number of Guest</legend>
                                    <div className='row'>
                                        <div className='col-6'>
                                            <Form.Label htmlFor="numberOfAdults">Adult </Form.Label>
                                            <Form.Control
                                                required
                                                type="number"
                                                id="numberOfAdults"
                                                name="numberOfAdults"
                                                value={booking.numberOfAdults}
                                                placeholder="0"
                                                min={1}
                                                onChange={handleInputChange}
                                            />
                                            <Form.Control.Feedback type="invalid">
                                                Please select at least 1 adult.
                                            </Form.Control.Feedback>
                                        </div>

                                        <div className='col-6'>
                                            <Form.Label htmlFor="numberOfChildren">Children </Form.Label>
                                            <Form.Control
                                                required
                                                type="number"
                                                id="numberOfChildren"
                                                name="numberOfChildren"
                                                value={booking.numberOfChildren}
                                                placeholder="0"
                                                min={0}
                                                onChange={handleInputChange}
                                            />
                                        </div>
                                    </div>
                                </fieldset>
                                <div className='form-group mt-2 mb-2'>
                                    <button type='submit' className='btn btn-hotel'>Continue</button>
                                </div>
                            </Form>


                        </div>
                    </div>
                    <div className='col-md-6'>
                        {isSubmitted && (
                            <BookingSummary
                                booking={booking}
                                payment={calculatePayment()} // Calculated payment instead of function
                                isFormValid={validated}
                                onConfirm={handleFormSubmit}
                            />
                        )}
                    </div>
                </div>
            </div>
        </>
    );
};

export default BookingForm;
