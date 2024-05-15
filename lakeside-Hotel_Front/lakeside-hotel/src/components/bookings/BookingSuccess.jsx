import React from 'react'
import { useLocation } from 'react-router'

const BookingSuccess = () => {
    const location = useLocation()
    const message = location.state?.message
    const error = location.state?.error



    return (
        <div className='container'>
            <Header title="Booking Success" />
            <div>
                {message ? (
                    <div>
                        <h3 className='text-success'>Booking success !</h3>
                        <p className='text-success'>{message}</p>
                    </div>
                ) : (
                    <div>
                        <h3 className='text-danger'>Error Booking Room  !</h3>
                        <p className='text-danger'>{error}</p>
                    </div>
                )}
            </div>
        </div>
    )
}

export default BookingSuccess