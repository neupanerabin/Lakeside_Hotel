import React from 'react';
import { Link } from 'react-router-dom';

const Admin = () => {
    return (
        <section className='container mt-5'>
            {/* Main header for the Admin Panel */}
            <h2>Welcome to Admin Panel</h2>
            
            {/* Divider line for visual separation */}
            <hr />

            {/* Navigation links for managing rooms and bookings */}
            <Link to={"/existing-rooms"}>Manage Rooms</Link><br />
            <Link to={"/existing-bookings"}>Manage Bookings</Link>
        </section>
    );
};

export default Admin;
