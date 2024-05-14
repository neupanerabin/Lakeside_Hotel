import React, { useEffect, useState } from 'react'
import { bookRoom, getRoomTypes } from '../utils/ApiFunctions'
import { useNavigate, useParams } from 'react-router'
import momnet from "moment"

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

    const handleSubmit = (e) =>{
        e.preventDefault()
        const form = e.currentTarget
        if(form.checkValidity()=== false || !isGuestCountValid() || !isCheckOutDateValid()){
            e.stopPropagation()
        }else{
            setIsSubmitted(true )
        }

        setIsValidated(true)
    }

    const handleBooking = async() =>{
        try{
            const confirmationCode = await bookRoom(roomId, booking)
            setIsSubmitted(true)
            navigate(" /", {message : confirmationCode}) 

        }catch(error){
            setErrorMessage(error.message)
            navigate(" /", {state : {error: errorMessage } })
        }
    }

    return (
        <div>BookingForm</div>
    )
}

export default BookingForm