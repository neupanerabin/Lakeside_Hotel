import React, { useState } from 'react'

const BookingForm = () => {

    const[ isValidated, setIsValidated] = useState(false)
    const[isSubmitted, setIsSubmitted] = useState(false)
    const[errorMessage, setErrorMessage] = useState("") 
  return (
    <div>BookingForm</div>
  )
}

export default BookingForm