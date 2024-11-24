import React, { useState } from 'react';
import { sendMove } from '../api';

const MoveInput = () => {
  const [move, setMove] = useState('');

  const handleMove = () => {
    const moveData = { move };  // Adjust structure based on backend expectation
    sendMove(moveData).then(response => {
      console.log('Move response:', response);
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