import React,{useEffect, useState} from 'react'
import { getRoomById, updateRoom } from '../utils/ApiFunctions';
import { useParams } from 'react-router';

const EditRoom = () => {
    const [newRoom, setRoom] = useState({
        photo: null,
        roomType: "",
        roomPrice: ""
      });

      const [successMessage, setSuccessMessage] = useState("");
      const [errorMessage, setErrorMessage] = useState("");
      const [imagePreview, setImagePreview] = useState("");
      const {roomId} = useParams()

      const handleImageChange = (e) => {
        const selectedImage = e.target.files[0];
        setRoom({ ...Room, photo: selectedImage });
        setImagePreview(URL.createObjectURL(selectedImage));
      }

      const handleRoomInputChange = (event) => {
        const {name, value} = event.target
        setRoom({...room, [name]: value })

      }

      useEffect(() => {
        const fetchRoom = async () =>{
          try{
            const roomData = await getRoomById(roomId)
            setRoom(roomData)
            setImagePreview(roomData.photo)
          } catch (error){
            console.error(error)
          }
        }
        fetchRoom()
      }, [roomId])



const handleSubmit = async (event) =>{
  event.preventDefault()

  try{
    const response = await updateRoom(roomId, room)
    if(response.status === 200){
      setSuccessMessage("Room updated successfully !")
      const updatedRoomData = await getRoomById(roomId)
      setRoom(updatedRoomData)
      setImagePreview(updatedRoomData.photo)
      setErrorMessage("")
    }else{
      setErrorMessage("Error updating room")
    }
  }catch(error){
    console.error(error)
    setErrorMessage(error.errorMessage)
  }
}


return (
  <section className="container mt-5 mb-5">
    <div className="row justify-content-center">
      <div className="col-md-8 col-lg-6">
        <h2 className="mt-5 mb-2">Add a New Room</h2>

        {successMessage && (
          <div className='alert alert-success fade show'>{successMessage}</div>
        )}
        {errorMessage && (
          <div className='alert alert-danger fade show'>{errorMessage}</div>
        )}

        <form onSubmit={handleSubmit}>
          <div className="mb-3">
            <label htmlFor="roomType" className="form-label">
              Room Type</label>
          </div>
          <div>
            <RoomTypeSelector
              // newRoom={newRoom.roomType} 
              handleRoomInputChange={handleRoomInputChange}
              newRoom={newRoom}

            />
          </div>

          <div className="mb-3">
            <label htmlFor="roomPrice" className="form-label">Room Price</label>
            <input
              className="form-control"
              required
              id="roomPrice"
              type="number"
              name="roomPrice"
              value={newRoom.roomPrice}
              onChange={handleRoomInputChange}
            />
          </div>
          <div className="mb-3">
            <label htmlFor="photo" className="form-label">Photo</label>
            <input
              id="photo"
              name="photo"
              type="file"
              className="form-control"
              onChange={handleImageChange}
            />
            {imagePreview && (
              <img
                src={imagePreview}
                alt="Preview Room Photo"
                style={{ maxWidth: "400px", maxHeight: "400px" }}
                className="mb-3"
              />
            )}
          </div>
          <button type="submit" className="btn btn-outline-primary ml-5" disabled={loading}>
            {loading ? 'Saving...' : 'Save Room'}
          </button>
        </form>
      </div>
    </div>
  </section>
);
};





export default EditRoom