import React from 'react';

const Header = ({ title }) => {
    return (
        <header className='header'>
            <div className='overlay'></div> {/* Overlay for styling purposes */}
            <div className='container'>
                <h1 className='header-title text-center'>{title}</h1> {/* Display the title */}
            </div>
        </header>
    );
}

export default Header;
