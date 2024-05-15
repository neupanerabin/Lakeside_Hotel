import React, { useEffect, useState } from 'react'
import { bookRoom, getRoomTypes } from '../utils/ApiFunctions'
import { useNavigate, useParams } from 'react-router'
import momnet from "moment"
import { Form } from 'react-bootstrap'

const BookingForm = () => {

    const [isValidated, setIsValidated] = useState(false)
    const [isSubmitted, setIsSubmitted] = useState(false)
    const [errorMessage, setErrorMessage] = useState("")
    const [roomPrice, setRoomPrice] = useState(0)
    const [booking, setBooking] = useState({
        guestName: " ",
        guestEmail: " ",
        guestInDate: " ",
        guestOutDate: " ",
        numberOfAdults: " ",
        numberOfChildren: " "
    })


    const [roomInfo, setRoomInfo] = useState({
        photo: "",
        getRoomType: "",
        roomPrice: ""

    })

    const { roomId } = useParams()
    const navigate = useNavigate()

    const handleInputChange = (e) => {
        const { name, value } = e.target
        setBooking({ ...booking, [name]: value })
        setErrorMessage("")
    }
    const getRoomPriceById = async (roomId) => {
        try {
            const response = await getRoomById(roomId)
            setRoomPrice(response.roomPrice)
        } catch (error) {
            throw new Error(error)
        }
    }

    useEffect(() => {
        getRoomPriceById(roomId)
    }, [roomId])

    const calculatePayment = () => {
        const checkInDate = momnet(booking.checkInDate)
        const checkOutDate = moment(booking.checkOutDate)
        const diffInDays = checkOutDate.diff(checkInDate)
        const price = roomPrice ? roomPrice : 0
        return diffInDays * price
    }

    const isGuestCountValid = () => {
        const adultCount = parseInt(booking.numberOfAdults)
        const childrenCount = parseInt(booking.numberOfChildren)
        const totalCount = adultCount + childrenCount
        return totalCount >= 1 && adultCount >= 1
    }

    const isCheckOutDateValid = () => {
        if (!moment(booking.checkOutDate).isSameOrAfter(momnet(booking.checkInDate))) {
            setErrorMessage("check-out date must be come before check-in date")
            return false
        } else {
            setErrorMessage("")
            return true
        }
    }

    const handleSubmit = (e) => {
        e.preventDefault()
        const form = e.currentTarget
        if (form.checkValidity() === false || !isGuestCountValid() || !isCheckOutDateValid()) {
            e.stopPropagation()
        } else {
            setIsSubmitted(true)
        }

        setIsValidated(true)
    }

    const handleBooking = async () => {
        try {
            const confirmationCode = await bookRoom(roomId, booking)
            setIsSubmitted(true)
            navigate(" /", { state: { message: confirmationCode } })

        } catch (error) {
            setErrorMessage(error.message)
            navigate(" /", { state: { error: errorMessage } })
        }
    }

    return (
        <>

            <div className='container mb-5'>
                <div className='row'>
                    <div className='col-md-6'>
                        <div className='card card-body mt-5'>
                            <h4 className='card card-title'>Reserved Room</h4>
                            <Form noValidate validated={validated} onSubmit={handleSubmit}>
                                <Form.Group>
                                    <Form.Label htmlFor="guestName">Full Name: </Form.Label>
                                    <Form.Control
                                        required
                                        type="text"
                                        id="guestName"
                                        name="guestName"
                                        value={booking.guestName}
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
                                                <Form.Label htmlFor="numberOfAdults">Adult: </Form.Label>
                                                <Form.Control
                                                    required
                                                    type="number"
                                                    id="numberOfAdults"
                                                    name="numberOfAdults"
                                                    value={booking.numberOfAdults}
                                                    placeholder="Enter your check-in Date"
                                                    onChange={handleInputChange}
                                                />
                                                <Form.Control.Feedback type="invalid">
                                                    Please select a check in date
                                                </Form.Control.Feedback>
                                        </div>
                                        </div>
                                </fieldset>


                            </Form>

                        </div>

                    </div>

                </div>
            </div>



        </>
    )
}

export default BookingForm