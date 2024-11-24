import React, { useEffect, useState } from 'react';
import { fetchGameData } from '../api';

const GameList = () => {
  const [games, setGames] = useState([]);

  useEffect(() => {
    fetchGameData().then(data => setGames(data || []));
  }, []);

  return (
    <div>
      <h1>Available Games</h1>
      <ul>
        {games.map((game, index) => (
          <li key={index}>{game.name}</li>
        ))}
      </ul>
    </div>
  );
};

export default GameList;