import React, { useState } from 'react';
import { joinGame } from '../api';

const JoinGame = () => {
  const [playerName, setPlayerName] = useState('');
  const [message, setMessage] = useState('');

  const handleJoin = () => {
    joinGame(playerName).then(response => {
      setMessage(response.message || playerName + ' Joined successfully!');
    });
  };

  return (
    <div>
      <h2>Join Game</h2>
      <input
        type="text"
        placeholder="Enter your name"
        value={playerName}
        onChange={(e) => setPlayerName(e.target.value)}
      />
      <button onClick={handleJoin}>Join</button>
      <p>{message}</p>
    </div>
  );
};

export default JoinGame;