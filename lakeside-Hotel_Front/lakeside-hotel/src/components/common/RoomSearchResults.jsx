import React, { useState } from 'react';
import { Button, Row } from 'react-bootstrap';
import RoomCard from '../room/RoomCard';
import RoomPaginator from './RoomPaginator';

// The RoomSearchResults component receives results and onClearSearch as props
const RoomSearchResults = ({ results, onClearSearch }) => {
  // State to manage the current page
  const [currentPage, setCurrentPage] = useState(1);

  // Constants for pagination
  const resultPerPage = 3;
  const totalResults = results.length;
  const totalPages = Math.ceil(totalResults / resultPerPage);

  // Function to handle page change
  const handlePageChange = (pageNumber) => {
    setCurrentPage(pageNumber);
  };

  // Calculate the start and end indices for the current page's results
  const startIndex = (currentPage - 1) * resultPerPage;
  const endIndex = startIndex + resultPerPage;
  const paginatedResults = results.slice(startIndex, endIndex);

  return (
    <>
      {results.length > 0 ? (
        <>
          <h5 className='text-center mt-5'>Search Results</h5>
          <Row>
            {paginatedResults.map((room) => (
              // RoomCard component to display individual room details
              <RoomCard key={room.id} room={room} />
            ))}
          </Row>

          <Row>
            {totalResults > resultPerPage && (
              // RoomPaginator component to handle pagination controls
              <RoomPaginator
                currentPage={currentPage}
                totalPages={totalPages}
                onPageChange={handlePageChange}
              />
            )}
          </Row>

          <Button variant="secondary" onClick={onClearSearch}>
            Clear Search
          </Button>
        </>
      ) : (
        // No search results found
        <p>No results found</p>
      )}
    </>
  );
};

export default RoomSearchResults;
