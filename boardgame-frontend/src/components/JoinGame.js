import React, { useState } from 'react';
import { joinGame } from '../api';

const JoinGame = () => {
  const [gameName, setPlayerName] = useState('');
  const [message, setMessage] = useState('');
  const [isHidden, setIsHidden] = useState(false);

  const handleJoin = () => {
    joinGame(gameName).then(response => {
      setMessage(response.message || gameName + ' Joined successfully!');
      setIsHidden(true);
    });
  };

  return (
    <div className={`JoinForm ${isHidden ? "hide" : ""}`}>
      <h2>Join Game</h2>
      <input
        type="text"
        placeholder="Enter game name"
        value={gameName}
        onChange={(e) => setPlayerName(e.target.value)}
      />
      <button onClick={handleJoin}>Join</button>
      <p>{message}</p>
    </div>
  );
};

export default JoinGame;
