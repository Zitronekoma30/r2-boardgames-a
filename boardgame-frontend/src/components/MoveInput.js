import React, { useState } from 'react';
import { sendMove } from '../api';

const MoveInput = () => {
  const [move, setMove] = useState('');
  const playerID = 'dummy';

  const handleMove = () => {
    //convert MoveInput into correct value types and split it apart
    const moveParts = move.split(',').map(value => value.trim());

    // Validate that we have exactly 4 parts
    if (moveParts.length !== 4) {
      console.error('Invalid move format. Please enter the move in the format "0,1,1,1".');
      return;
    }

    const [fromX, fromY, toX, toY] = moveParts.map(Number);

    const moveData = [fromX, fromY, toX, toY, playerID]; // Adjust structure based on backend expectation

    sendMove(moveData).then(response => {
      console.log('Move response:', response);
    })
    .catch(error => {
      console.error('Failed to send move:', error);
    });
  };

  return (
    <div>
      <h2>Make a Move</h2>
      <input
        type="text"
        placeholder="Enter your move"
        value={move}
        onChange={(e) => setMove(e.target.value)}
      />
      <button onClick={handleMove}>Submit Move</button>
    </div>
  );
};

export default MoveInput;