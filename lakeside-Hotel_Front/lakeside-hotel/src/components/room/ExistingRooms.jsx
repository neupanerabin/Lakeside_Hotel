import React, { useEffect, useState } from 'react'
import { Link } from 'react-router-dom' // Import Link from react-router-dom
import { deleteRoom, getAllrooms } from '../utils/ApiFunctions'
import { Col } from "react-bootstrap"
import RoomPaginator from '../common/RoomPaginator'
import RoomFilter from '../common/RoomFilter'
import { FaTrashAlt, FaEye, FaEdit } from "react-icons/fa" // Import FaEdit from react-icons/fa

const ExistingRooms = () => {
  const [rooms, setRooms] = useState([])
  const [currentPage, setCurrentPage] = useState(1)
  const [roomsPerPage] = useState(8)
  const [isLoading, setIsLoading] = useState(false)
  const [filteredRooms, setFilteredRooms] = useState([])
  const [selectedRoomType, setSelectedRoomType] = useState("")
  const [successMessage, setSuccessMessage] = useState("")
  const [errorMessage, setErrorMessage] = useState("")

  useEffect(() => {
    fetchRooms()
  }, [])

  const fetchRooms = async () => {
    setIsLoading(true)
    try {
      const result = await getAllrooms()
      setRooms(result)
      setIsLoading(false)
    } catch (error) {
      setErrorMessage(error.message)
    }
  }

  useEffect(() => {
    if (selectedRoomType === "") {
      setFilteredRooms(rooms)
    }
    else {
      const filtered = rooms.filter((room) => room.roomType === selectedRoomType)
      setFilteredRooms(filtered)
    }
    setCurrentPage(1)
  }, [rooms, selectedRoomType])

  const handlePaginationClick = (pageNumber) => {
    setCurrentPage(pageNumber)
  }

  const calculateTotalPages = (filteredRooms, roomsPerPage, rooms) => {
    const totalRooms = filteredRooms.length > 0 ? filteredRooms.length : rooms.length
    return Math.ceil(totalRooms / roomsPerPage)
  }

  const handleDelete = async (roomId) => {
    try {
      const result = await deleteRoom(roomId)
      if (result === "") {
        setSuccessMessage(`Room No ${roomId} was delete`)
        fetchRooms()
      }
      else {
        console.error(`Error deleting room: ${result.message}`)
      }
    } catch (error) {
      setErrorMessage(error)
    }
    setTimeout(() => {
      setSuccessMessage("")
      setErrorMessage("")
    }, 3000)
  }

  const indexOfLastRoom = currentPage * roomsPerPage
  const indexOfFirstRoom = currentPage * roomsPerPage - roomsPerPage // Correct calculation
  const currentRooms = filteredRooms.slice(indexOfFirstRoom, indexOfLastRoom)

  return (
    <>
      {isLoading ? (
        <p> Loading existing rooms</p>
      ) : (
        <>
          <section className="mt-5 mb-5 container">
            <div className="d-flex justify-content-center mb-3 mt-5">
              <h2>Existing Rooms </h2>
            </div>
            <Col md={6} className="mb-3 mb-md-0">
              <RoomFilter data={rooms} setFilteredRooms={setFilteredRooms} />
            </Col>
            <table className='table table-bordered table-hover'>
              <thead>
                <tr className="text-center">
                  <th>ID</th>
                  <th>RoomType</th>
                  <th>Room Price</th>
                  <th>Actions</th>
                </tr>
              </thead>
              <tbody>
                {currentRooms.map((room) => (
                  <tr key={room.id} className='text-center'>
                    <td>{room.id}</td>
                    <td>{room.roomType}</td>
                    <td>{room.roomPrice}</td>
                    <td>
                      <Link to={`/edit-room/${room.id}`}>
                        <span className="btn btn-info btn-sm">
                          <FaEye />
                          View
                        </span>
                      </Link>
                      <span className="btn btn-warning btn-sm">
                        <FaEdit />
                        Edit
                      </span>
                      <button
                        className="btn btn-danger btn-sm"
                        onClick={() => handleDelete(room.id)}
                      >
                        <FaTrashAlt />
                      </button>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
            <RoomPaginator
              currentPage={currentPage}
              totalPages={calculateTotalPages(filteredRooms, roomsPerPage, rooms)}
              onPageChange={handlePaginationClick}
            />
          </section>
        </>
      )}
    </>
  )
}

export default ExistingRooms
