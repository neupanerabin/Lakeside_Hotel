import React from 'react'
import MainHeader from '../layouts/MainHeader'
import HotelService from '../common/HotelService'
import Parallax from '../common/Parallax'

const Home = () => {
  return (
    <section>
      <MainHeader/>
      <section className='container'>
        <Parallax/>
        <HotelService></HotelService>
        <Parallax/>
      </section>
    </section>
  )
}

export default Home