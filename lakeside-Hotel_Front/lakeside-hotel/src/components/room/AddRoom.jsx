import React, { useState } from 'react';
import { addRoom } from "../utils/ApiFunctions";
import { RoomTypeSelector } from '../common/RoomTypeSelector';
import { Link } from 'react-router-dom';

const AddRoom = () => {
  const [newRoom, setNewRoom] = useState({
    photo: null,
    roomType: "",
    roomPrice: ""
  });
  const [successMessage, setSuccessMessage] = useState("");
  const [errorMessage, setErrorMessage] = useState("");
  const [imagePreview, setImagePreview] = useState("");
  const [loading, setLoading] = useState(false); // Added loading state

  const handleRoomInputChange = (e) => {
    const { name, value } = e.target;

    if (name === "roomPrice") {
      if (!isNaN(value)) {
        setNewRoom({ ...newRoom, [name]: parseInt(value, 10) });
      } else {
        setNewRoom({ ...newRoom, [name]: "" });
      }
    } else {
      setNewRoom({ ...newRoom, [name]: value });
    }
  };

  const handleImageChange = (e) => {
    const selectedImage = e.target.files[0];
    setNewRoom({ ...newRoom, photo: selectedImage });
    setImagePreview(URL.createObjectURL(selectedImage));
  }

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);

    try {
      const success = await addRoom(newRoom.photo, newRoom.roomType, newRoom.roomPrice);

      if (success) {
        setSuccessMessage("A New Room was Added Successfully");
        setNewRoom({ photo: null, roomType: "", roomPrice: "" });
        setImagePreview("");
        setErrorMessage("");
      } else {
        setErrorMessage("Error Adding Room");
      }
    } catch (error) {
      setErrorMessage(error.message);
    } finally {
      setLoading(false); // Reset loading state
    }

    setTimeout(() => {
      setSuccessMessage("");
      setErrorMessage("");
    }, 3000);
  };

  return (
    <section className="container mt-5 mb-5">
      <div className="row justify-content-center">
        <div className="col-md-8 col-lg-6">
          <h2 className="mt-5 mb-2">Add a New Room</h2>

          {successMessage && <div className='alert alert-success fade show'>{successMessage}</div>}
          {errorMessage && <div className='alert alert-danger fade show'>{errorMessage}</div>}
          {loading && <div className='alert alert-info fade show'>Loading...</div>} {/* Added loading indicator */}

          <form onSubmit={handleSubmit}>
            <div className="mb-3">
              <label htmlFor="roomType" className="form-label">Room Type</label>
              <RoomTypeSelector
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
                  src={`data:image/jpeg;base64,${imagePreview}`}
                  alt="Room Preview"
                  style={{ maxWidth: "400px", maxHeight: "400px" }}
                  className="mt-3"
                />
              )}
            </div>

            <div className="d-grid gap-2 d-md-flex mt-2">
              <Link to={"/existing-rooms"} className="btn btn-outline-info">Back</Link>
              <button type="submit" className="btn btn-outline-primary ml-5">Save Room</button>
            </div>
          </form>
        </div>
      </div>
    </section>
  );
};

export default AddRoom;
