import React, { useEffect, useState } from 'react';
import { fetchBoard } from '../api';

const GameBoard = () => {
  const [board, setBoard] = useState(null);

  useEffect(() => {
    // Fetch board data when the component mounts
    fetchBoard().then(data => setBoard(data));
  }, []);

  if (!board) return <p>Loading board...</p>;

  return (
    <div>
      <h2>Game Board</h2>
      <pre>{JSON.stringify(board, null, 2)}</pre> {/* For debugging */}
      {/* Replace this with actual board rendering logic */}
    </div>
  );
};

export default GameBoard;