import React, { useState } from 'react';
import playerName from './JoinGame';

const Turn = () => {
  const [playerName, setPlayerName] = useState('');
  const [turnMessage, setTurnMessage] = useState('');

  const TurnMessage = () => {
    playerName(playerName).then(response => {
      setTurnMessage(response.turnMessage || 'Made Move successfully!')
    })
  };

  return (
    <div class="Turn">
      {TurnMessage}
    </div>
  )

}

export default Turn;