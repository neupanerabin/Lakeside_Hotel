import React, { useEffect, useState } from 'react';
import { getRoomById, updateRoom } from '../utils/ApiFunctions';
import { Link, useParams } from 'react-router-dom';

const EditRoom = () => {
  const [newRoom, setRoom] = useState({
    photo: "",
    roomType: "",
    roomPrice: ""
  });

  const [loading, setLoading] = useState(false);
  const [successMessage, setSuccessMessage] = useState("");
  const [errorMessage, setErrorMessage] = useState("");
  const [imagePreview, setImagePreview] = useState("");
  const { roomId } = useParams();

  // Handle image selection and preview
  const handleImageChange = (e) => {
    const selectedImage = e.target.files[0];
    setRoom({ ...newRoom, photo: selectedImage });
    setImagePreview(URL.createObjectURL(selectedImage));
  };

  // Handle form input changes
  const handleRoomInputChange = (event) => {
    const { name, value } = event.target;
    setRoom({ ...newRoom, [name]: value });
  };

  // Fetch room data by ID on component mount
  useEffect(() => {
    const fetchRoom = async () => {
      try {
        const roomData = await getRoomById(roomId);
        setRoom(roomData);
        setImagePreview(roomData.photo); // Assuming photo is in base64 or URL format
      } catch (error) {
        console.error(error);
        setErrorMessage("Error fetching room data");
      }
    };
    fetchRoom();
  }, [roomId]);

  // Handle form submission to update room details
  const handleSubmit = async (event) => {
    event.preventDefault();
    setLoading(true);
    try {
      const response = await updateRoom(roomId, newRoom);
      if (response.status === 200) {
        setSuccessMessage("Room updated successfully!");
        const updatedRoomData = await getRoomById(roomId);
        setRoom(updatedRoomData);
        setImagePreview(updatedRoomData.photo);
        setErrorMessage("");
      } else {
        setErrorMessage("Error updating room");
      }
    } catch (error) {
      console.error(error);
      setErrorMessage("An error occurred while updating the room");
    } finally {
      setLoading(false);
    }
  };

  return (
    <section className="container mt-5 mb-5">
      <h3 className="text-center mb-5 mt-5">Edit Room</h3>
      <div className="row justify-content-center">
        <div className="col-md-8 col-lg-6">
          {successMessage && (
            <div className='alert alert-success' role="alert">{successMessage}</div>
          )}
          {errorMessage && (
            <div className='alert alert-danger'>{errorMessage}</div>
          )}

          <form onSubmit={handleSubmit}>
            <div className="mb-3">
              <label htmlFor="roomType" className="form-label hotel-color">
                Room Type
              </label>
              <input
                type="text"
                className="form-control"
                id="roomType"
                name="roomType"
                value={newRoom.roomType}
                onChange={handleRoomInputChange}
              />
            </div>
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
                Photo
              </label>
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
                  src={imagePreview} // Ensure imagePreview is in the correct format (URL or base64)
                  alt="Room Preview"
                  style={{ maxWidth: "400px", maxHeight: "400px" }}
                  className="mt-3"
                />
              )}
            </div>
            <div className="d-grid gap-2 d-md-flex mt-2">
              <Link to={"/existing-rooms"} className="btn btn-outline-info">
                Back
              </Link>
              <button type="submit" className="btn btn-outline-primary" disabled={loading}>
                {loading ? 'Updating...' : 'Edit Room'}
              </button>
            </div>
          </form>
        </div>
      </div>
    </section>
  );
};

export default EditRoom;
