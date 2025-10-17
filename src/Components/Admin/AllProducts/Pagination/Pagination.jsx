import React from 'react'
import './Pagination.css'

const Pagination = ({ totalPages, currentPage, onPageChange }) => {
    const getPageNumbers = () => {
      const pageNumbers = [];
      if (totalPages <= 5) {
        // Show all pages if there are 5 or fewer
        for (let i = 1; i <= totalPages; i++) {
          pageNumbers.push(i);
        }
      } else {
        // Show range with ellipsis for larger page counts
        if (currentPage <= 3) {
          pageNumbers.push(1, 2, 3, 4, '...', totalPages);
        } else if (currentPage >= totalPages - 2) {
          pageNumbers.push(1, '...', totalPages - 3, totalPages - 2, totalPages - 1, totalPages);
        } else {
          pageNumbers.push(1, '...', currentPage - 1, currentPage, currentPage + 1, '...', totalPages);
        }
      }
      return pageNumbers;
    };
  
    return (
      <div className="pagination">
        <button
          onClick={() => onPageChange(currentPage - 1)}
          disabled={currentPage === 1}
          className="btn btn-primary"
        >
          Previous
        </button>
        {getPageNumbers().map((number, idx) =>
          typeof number === 'number' ? (
            <button
              key={idx}
              onClick={() => onPageChange(number)}
              className={`btn ${number === currentPage ? 'btn-primary active' : 'btn-secondary'}`}
            >
              {number}
            </button>
          ) : (
            <span key={idx} className="pagination-ellipsis">
              {number}
            </span>
          )
        )}
        <button
          onClick={() => onPageChange(currentPage + 1)}
          disabled={currentPage === totalPages}
          className="btn btn-primary"
        >
          Next
        </button>
      </div>
    );
  };
  
export default Pagination
