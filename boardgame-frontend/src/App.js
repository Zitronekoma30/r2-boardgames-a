import './App.css';
import React from 'react';
import GameBoard from './components/GameBoard';
import JoinGame from './components/JoinGame';
import PlayerHand from './components/PlayerHand';
import Turn from './components/Turn';

function App() {
  return (
    <div className="App">
      <JoinGame />
      <Turn />
      <GameBoard />
      <PlayerHand />
    </div>
  );
}

export default App;
