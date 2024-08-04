import React from 'react';
import { Card, Col, Container, Row } from 'react-bootstrap';
import Header from './Header';
import { FaClock, FaCocktail, FaParking, FaSnowflake, FaTshirt, FaUtensils, FaWifi } from "react-icons/fa";

const HotelService = () => {
    return (
        <>
            <Container className='mb-2'>
                <Header title={"Our Services"} /> {/* Header component with title */}
                <Row className='mt-4'>
                    <h4 className='text-center'>
                        Services at <span className='hotel-color'>lakeSide -</span> Hotel   
                        <span className='gap-2'>
                             <FaClock /> - 24-Hour Front Desk {/* Icon for 24-hour front desk service */}
                        </span>
                    </h4>
                </Row>
                <hr />
                <Row xs={1} md={2} lg={3} className='g-4 mt-2'> {/* Responsive grid layout */}
                    {/* Each Col contains a Card component describing a service */}
                    <Col>
                        <Card>
                            <Card.Body>
                                <Card.Title className='hotel-color'>
                                    <FaWifi /> WiFi {/* Icon and title for WiFi service */}
                                </Card.Title>
                                <Card.Text>Stay connected with high-speed internet access.</Card.Text>
                            </Card.Body>
                        </Card>
                    </Col>
                    <Col>
                        <Card>
                            <Card.Body>
                                <Card.Title className='hotel-color'>
                                    <FaUtensils /> Breakfast {/* Icon and title for Breakfast service */}
                                </Card.Title>
                                <Card.Text>Start your day right with our delicious breakfast options.</Card.Text>
                            </Card.Body>
                        </Card>
                    </Col>
                    <Col>
                        <Card>
                            <Card.Body>
                                <Card.Title className='hotel-color'>
                                    <FaTshirt /> Laundry {/* Icon and title for Laundry service */}
                                </Card.Title>
                                <Card.Text>Keep your clothes clean and fresh with our laundry service.</Card.Text>
                            </Card.Body>
                        </Card>
                    </Col> 
                    <Col>
                        <Card>
                            <Card.Body>
                                <Card.Title className='hotel-color'>
                                    <FaCocktail /> Mini Bar {/* Icon and title for Mini Bar service */}
                                </Card.Title>
                                <Card.Text>Enjoy a refreshing drink or snack from our in-room mini bar.</Card.Text>
                            </Card.Body>
                        </Card>
                    </Col>
                    <Col>
                        <Card>
                            <Card.Body>
                                <Card.Title className='hotel-color'>
                                    <FaParking /> Parking {/* Icon and title for Parking service */}
                                </Card.Title>
                                <Card.Text>Park your car conveniently in our on-site parking lot.</Card.Text>
                            </Card.Body>
                        </Card>
                    </Col>
                    <Col>
                        <Card>
                            <Card.Body>
                                <Card.Title className='hotel-color'>
                                    <FaSnowflake /> Air Conditioning {/* Icon and title for Air Conditioning */}
                                </Card.Title>
                                <Card.Text>Stay cool and comfortable with our air conditioning system.</Card.Text>
                            </Card.Body>
                        </Card>
                    </Col>
                </Row>
            </Container>
        </>
    );
}

export default HotelService;
