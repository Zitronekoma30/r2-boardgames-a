import './App.css';
import React from 'react';
import GameBoard from './components/GameBoard';
import JoinGame from './components/JoinGame';
import MoveInput from './components/MoveInput';
import Turn from './components/Turn';

function App() {
  return (
    <div className="App">
      <JoinGame />
      <Turn />
      <GameBoard />
      <MoveInput />
    </div>
  );
}

export default App;
