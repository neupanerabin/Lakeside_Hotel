import moment from 'moment';
import React, { useState } from 'react';
import { getAvailableRooms } from '../utils/ApiFunctions';
import { Col, Container, Row, Form, Button } from 'react-bootstrap';
import RoomTypeSelector from "./RoomTypeSelector";
import RoomSearchResults from './RoomSearchResults';

const RoomSearch = () => {
  const [searchQuery, setSearchQuery] = useState({
    checkInDate: "",
    checkOutDate: "",
    roomType: ""
  });

  const [errorMessage, setErrorMessage] = useState("");
  const [availableRooms, setAvailableRooms] = useState([]);
  const [isLoading, setIsLoading] = useState(false);
  const [isSearched, setIsSearched] = useState(false);

  const handleSearch = (e) => {
    e.preventDefault();
    const checkIn = moment(searchQuery.checkInDate);
    const checkOut = moment(searchQuery.checkOutDate);

    if (!checkIn.isValid() || !checkOut.isValid()) {
      setErrorMessage("Please, enter a valid date range");
      return;
    }
    if (!checkOut.isSameOrAfter(checkIn)) {
      setErrorMessage("Check-In Date must come before Check-Out Date");
      return;
    }

    setIsLoading(true);
    setErrorMessage("");
    setIsSearched(true);

    getAvailableRooms(searchQuery.checkInDate, searchQuery.checkOutDate, searchQuery.roomType)
      .then((response) => {
        setAvailableRooms(response.data);
      })
      .catch((error) => {
        console.error(error);
        setErrorMessage("Error fetching available rooms");
      })
      .finally(() => {
        setIsLoading(false);
      });
  };

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setSearchQuery({ ...searchQuery, [name]: value });

    if (name === 'checkInDate' || name === 'checkOutDate') {
      const checkIn = moment(name === 'checkInDate' ? value : searchQuery.checkInDate);
      const checkOut = moment(name === 'checkOutDate' ? value : searchQuery.checkOutDate);

      if (checkIn.isValid() && checkOut.isValid() && checkOut.isSameOrAfter(checkIn)) {
        setErrorMessage("");
      }
    }
  };

  const clearSearch = () => {
    setSearchQuery({
      checkInDate: "",
      checkOutDate: "",
      roomType: ""
    });
    setAvailableRooms([]);
    setIsSearched(false);
  };

  return (
    <>
      <Container className='mt-5 mb-5 py-5 shadow'>
        <Form onSubmit={handleSearch}>
          <Row className="justify-content-center">
            <Col xs={12} md={3}>
              <Form.Group controlId='checkInDate'>
                <Form.Label>Check-In Date</Form.Label>
                <Form.Control
                  type='date'
                  name='checkInDate'
                  value={searchQuery.checkInDate}
                  onChange={handleInputChange}
                  min={moment().format("YYYY-MM-DD")}
                />
              </Form.Group>
            </Col>

            <Col xs={12} md={3}>
              <Form.Group controlId='checkOutDate'>
                <Form.Label>Check-Out Date</Form.Label>
                <Form.Control
                  type='date'
                  name='checkOutDate'
                  value={searchQuery.checkOutDate}
                  onChange={handleInputChange}
                  min={moment().format("YYYY-MM-DD")}
                />
              </Form.Group>
            </Col>

            <Col xs={12} md={3}>
              <Form.Group>
                <Form.Label>Room Type</Form.Label>
                <div className='d-flex'>
                  <RoomTypeSelector
                    handleRoomInputChange={handleInputChange}
                    newRoom={searchQuery}
                  />
                  <Button variant='secondary' type='submit'>Search</Button>
                </div>
              </Form.Group>
            </Col>
          </Row>
        </Form>

        {isLoading ? (
          <p>Finding available rooms...</p>
        ) : isSearched && availableRooms.length > 0 ? (
          <RoomSearchResults
            results={availableRooms}
            onClearSearch={clearSearch}
          />
        ) : isSearched ? (
          <p>No rooms available for the selected date and room type</p>
        ) : null}
        {errorMessage && <p className='text-danger'>{errorMessage}</p>}
      </Container>
    </>
  );
};

export default RoomSearch;
