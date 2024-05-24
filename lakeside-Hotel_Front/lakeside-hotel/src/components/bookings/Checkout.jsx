import React, { useEffect, useState } from 'react'
import BookingForm from './BookingForm'
import { getRoomById } from '../utils/ApiFunctions'
import { useParams } from 'react-router'

const Checkout = () => {
  const [error, setError] = useState("")
  const[isLoading, setIsLoading] = useState(true)
  const[roomInfo, setRoomInfo] = useState({photo: "", roomType: " ", roomPrice: ""})

  const{roomId} = useParams()



  useEffect(() =>{
    setTimeout(() =>{
      getRoomById(rooomId)
      .then((response) =>{
        setRoomInfo(response)
        setIsLoading(false)
      }).catch((error) =>{
        setError(error)
        setIsLoading(false)
      })
    },2000)
  }, [roomId])

  return (
    <div>
      <section className='container'>
        <div className='row flex-column flex-md-row align-items-center'>
          <div>
            
          </div>

        </div>

      </section>
    </div>
  )
}

export default Checkout