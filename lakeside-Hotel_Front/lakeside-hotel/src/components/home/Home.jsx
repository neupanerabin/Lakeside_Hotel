import React from 'react'
import MainHeader from '../layouts/MainHeader'
import HotelService from '../common/HotelService'
import Parallax from '../common/Parallax'
import RoomCarousel from '../common/RoomCarousel'
import RoomSearch from '../common/RoomSearch'
import { useLocation } from 'react-router'


const Home = () => {

const location = useLocation()
const message = location.state && location.state.message
const currentUser = localStorage.getItem("userId")
  return (
    <section>
      {message && <p className='text-warning px-5'>{message}</p>}
      {currentUser && <h6 className='text-success text-center'>You are Logged-In as{currentUser}</h6>}
      <MainHeader/>
      <div className='container'>
        <RoomSearch/>
        <RoomCarousel/>
        <Parallax/>
        <RoomCarousel/>
        <HotelService></HotelService>
        <Parallax/>
      </div>
    </section>
  )
}

export default Home