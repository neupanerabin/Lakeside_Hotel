import axios from "axios";

// Create an axios instance with a base URL
export const api = axios.create({
    baseURL: "http://localhost:8080"
});

// This function adds a new room to the database
export async function addRoom(photo, roomType, roomPrice) {
    // Create a FormData object to hold the room data
    const formData = new FormData();
    formData.append("photo", photo);
    formData.append("roomType", roomType);
    formData.append("roomPrice", roomPrice);

    try {
        // Make a POST request to add a new room
        const response = await api.post("/rooms/add/new-room", formData);
        // Return true if the request was successful
        return response.status === 200;
    } catch (error) {
        console.error("Error adding room:", error);
        return false;
    }
}

// This function gets all room types from the database
export async function getRoomTypes() {
    try {
        // Make a GET request to fetch all room types
        const response = await api.get("/rooms/room/types");
        return response.data;
    } catch (error) {
        throw new Error("Error fetching room types");
    }
}

// This function gets all rooms from the database
export async function getAllrooms() {
    try {
        // Make a GET request to fetch all rooms
        const result = await api.get("/rooms/all-rooms");
        return result.data;
    } catch (error) {
        throw new Error("Error fetching rooms");
    }
}

// This function deletes a room by its ID
export async function deleteRoom(roomId) {
    try {
        // Make a DELETE request to delete a room
        const result = await api.delete(`rooms/delete/room/${roomId}`);
        return result.data;
    } catch (error) {
        throw new Error(`Error deleting room ${error.message}`);
    }
}

// This function updates a room's details
export async function updateRoom(roomId, roomData) {
    // Create a FormData object to hold the updated room data
    const formData = new FormData();
    formData.append("roomType", roomData.roomType);
    formData.append("roomPrice", roomData.roomPrice);
    formData.append("roomPhoto", roomData.photo);

    // Make a PUT request to update the room
    const response = await api.put(`/rooms/update/${roomId}`, formData);
    return response;
}

// This function gets a room by its ID
export async function getRoomById(roomId) {
    try {
        // Make a GET request to fetch the room by its ID
        const result = await api.get(`/rooms/room/${roomId}`);
        return result.data;
    } catch (error) {
        throw new Error(`Error fetching room ${error.message}`);
    }
}

// This function saves a new booking to the database
export async function bookRoom(roomId, booking) {
    try {
        // Make a POST request to save the booking
        const response = await api.post(`/bookings/room/${roomId}/booking`, booking);
        return response.data;
    } catch (error) {
        if (error.response && error.response.data) {
            throw new Error(error.response.data);
        } else {
            throw new Error(`Error booking data : ${error.message}`);
        }
    }
}

// This function gets all bookings from the database
export async function getAllBookings() {
    try {
        // Make a GET request to fetch all bookings
        const result = await api.get("/bookings/all-bookings");
        return result.data;
    } catch (error) {
        throw new Error(`Error fetching bookings: ${error.message}`);
    }
}

// This function gets a booking by its confirmation code
export async function getBookingByConfirmationCode(confirmationCode) {
    try {
        // Make a GET request to fetch the booking by its confirmation code
        const result = await api.get(`/bookings/confirmation/${confirmationCode}`);
        return result.data;
    } catch (error) {
        if (error.response && error.response.data) {
            throw new Error(error.response.data);
        } else {
            throw new Error(`Error finding booking : ${error.message}`);
        }
    }
}

// This function cancels a booking by its ID
export async function cancelBooking(bookingId) {
    try {
        // Make a DELETE request to cancel the booking
        const result = await api.delete(`/bookings/booking/${bookingId}/delete`);
        return result.data;
    } catch (error) {
        throw new Error(`Error cancelling booking : ${error.message}`);
    }
}

// This function get All Available Rooms from database with a given date and a room type

export async function getAvailableRooms(checkInDate, checkOutDate, roomType){
    // Make get Request to fetch the available rooms 
    const result = await api.get(
        `rooms/available-rooms?checkInDate=${checkInDate}
        &checkOutDate=${checkOutDate}&roomTypes=${roomType}`)
        return result

}
