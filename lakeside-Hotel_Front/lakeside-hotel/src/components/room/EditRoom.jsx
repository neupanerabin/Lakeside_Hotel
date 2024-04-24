import React,{useEffect, useState} from 'react'
import { getRoomById, updateRoom } from '../utils/ApiFunctions';
import { Link, useParams } from 'react-router-dom';

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
  event.preventDefault();
  setLoading(true);

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
    <h3 className="text-center mb-5 mt-5">Edit Room</h3>
    <div className="row justify-content-center">
      <div className="col-md-8 col-lg-6">
        {successMessage && (
          <div className='alert alert-success ' role="alert">{successMessage}</div>
        )}
        {errorMessage && (
          <div className='alert alert-danger fade show'>{errorMessage}</div>
        )}

        <form onSubmit={handleSubmit}>
          <div className="mb-3">
            <label htmlFor="roomType" className="form-label hotel-color">
              Room Type</label>

              <input
              type="text"
              className="form-control"
              id="roomType"
              name="roomType"
              value={room.roomType}
              onChange={handleImageChange}
              />
          </div> 
          {/* <div>
            <RoomTypeSelector
              // newRoom={newRoom.roomType} 
              handleRoomInputChange={handleRoomInputChange}
              newRoom={newRoom}

            />
          </div> */}

          <div className="mt-3">
            <label htmlFor="roomPrice" className="form-label hotel-color">Room Price</label>
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
            <label htmlFor="photo" className="form-label hotel-color">
              Photo</label>
            <input
              required
              type="file"
              id="photo"
              name="photo"
              className="form-control"
              onChange={handleImageChange}
            />
            {imagePreview && (
              <img
                src={`data:image/jpeg;base64,${imagePreview}`}
                alt="Room Preview"
                style={{ maxWidth: "400px", maxHeight: "400px" }}
                className="mt-3"
              />
            )}
          </div>
          <div className="d-grid gap-2 d-md-flex mt-2">
            <Link to={"/existing-rooms"} className="btn btn-outline-info ml-5">
            Back
            </Link>
          <button type="submit" className="btn btn-outline-primary ml-5" >
            Edit Room
          </button>
          </div>
          
        </form>
      </div>
    </div>
  </section>
);
};





export default EditRoom