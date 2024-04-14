import React from 'react'

const EditRoom = () => {
    const [newRoom, setRoom] = useState({
        photo: null,
        roomType: "",
        roomPrice: ""
      });
      const [successMessage, setSuccessMessage] = useState("");
      const [errorMessage, setErrorMessage] = useState("");
      const [imagePreview, setImagePreview] = useState("");

      const handleImageChange = (e) => {
        const selectedImage = e.target.files[0];
        setRoom({ ...Room, photo: selectedImage });
        setImagePreview(URL.createObjectURL(selectedImage));
      }

      const handleRoomInputChange = (e) => {
        const name = e.target.name
        let value = e.target.value

     


    return (
        <div>

            <h2>EditRoom </h2>
        </div>
    )
}
}
export default EditRoom