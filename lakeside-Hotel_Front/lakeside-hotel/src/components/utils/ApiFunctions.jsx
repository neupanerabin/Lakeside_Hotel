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

// This function deletes room by the Id

export async function deleteRoom(roomId){
    try{
        const result = await api.delete(`rooms/delete/room/${roomId}`)
        return result.data

    }catch(error){
        throw new Error(`Error deleteing room ${error.message}`)

    }
}
// This function update the room
export async function updateRoom(roomId, roomData){
    const formData = new FormData()
    formData.append("roomType", roomData.roomType)
    formData.append("roomPrice", roomData.roomPrice)
    formData.append("roomPhoto", roomData.photo)

    const response = await api.put(`/rooms/update/${roomId}`)
    return response

}
// This function gets a room by hte id
export async function getRoomById(roomId, ){
    try{
        const result = await api.get(`/rooms/room/${roomId}`)
        return result.data

    }catch(error){
        throw new Error(`Error fetching room ${error.message}`)

    }
}

 