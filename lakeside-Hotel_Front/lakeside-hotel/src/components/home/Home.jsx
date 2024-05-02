import React from 'react'
import MainHeader from '../layouts/MainHeader'
import HotelService from '../common/HotelService'
import Parallax from '../common/Parallax'
import RoomCarousel from '../common/RoomCarousel'

const Home = () => {
  return (
    <section>
      <MainHeader/>
      <section className='container'>
        <RoomCarousel/>
        <Parallax/>
        <RoomCarousel/>
        <HotelService></HotelService>
        <Parallax/>
      </section>
    </section>
  )
}

export default Home