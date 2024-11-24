import './App.css';
import React from 'react';
import GameBoard from './components/GameBoard';
import JoinGame from './components/JoinGame';
import MoveInput from './components/MoveInput';

function App() {
  return (
    <div className="App">
      <JoinGame />
      <GameBoard />
      <MoveInput />
    </div>
  );
}

export default App;
