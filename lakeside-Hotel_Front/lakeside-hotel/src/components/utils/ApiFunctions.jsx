import axios from "axios";

export const api = axios.create({
    baseURL: "http://localhost:8081"
});

// This function adds a new room to the database 
export async function addRoom(photo, roomType, roomPrice) {
    const formData = new FormData();
    formData.append("photo", photo);
    formData.append("roomType", roomType);
    formData.append("roomPrice", roomPrice);

    try {
        const response = await api.post("/rooms/add/new-room", formData);
        return response.status === 200;
    } catch (error) {
        console.error("Error adding room:", error);
        return false;
    }
}

// This function gets all room types from the database
export async function getRoomTypes() {
    try {
        const response = await api.get("/rooms/room/types");
        return response.data;
    } catch (error) {
        throw new Error("Error fetching room types");
    }
}
// This functions gets all rooms from the database 

export async function getAllrooms(){
    try{
        const result = await api.get("/rooms/all-rooms")
        return result.data
    }catch(error){
        throw new Error("Error fetching  rooms")
    }
}