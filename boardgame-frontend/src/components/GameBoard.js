import React, { useEffect, useState } from 'react';
import { fetchBoard } from '../api';
import Board from './Board';

//adjust based on which game you want to try /TODO: make this work
const ROOT = '../../../..';
const GAME_PATH = ROOT + '/chess-demo';
const ASSET_PATH = GAME_PATH + '/src/view/assets';
const PIECE_PATH = ASSET_PATH + '/piece';
const TILE_PATH = ASSET_PATH + '/tiles/';

const TEMP_FILEPATH = '../assets/';


const GameBoard = () => {
  //create an Array of boardData that is currently an empty Array
  const [boardData, setBoardData] = useState([]);

  useEffect(() => {
    // Fetch board data when the component mounts
    fetchBoard().then(data => setBoardData(data));

  }, []);

    // Render function for tiles
      const renderTile = (tile) => (
        <img
          src={require(`../assets/${tile.sprite}`)}
          alt="tile"
          className="tile-img"
        />
      );

      // Render function for pieces
      const renderPiece = (piece, index) => (
        <img
          key={index}
          src={require(`../assets/${piece.sprite}`)}
          alt={piece.pieceName}
          className="piece-img"
          draggable="true"
        />
      );

  return (
    <div className='BoardArea'>
      <h2>Game Board</h2>
      {/*
        this gives us the data from the JSON as pure text to test if we get it
        <pre>{JSON.stringify(boardData, null, 2)}</pre> {}
      */}



      {boardData.length > 0 ? (
        <Board boardData={boardData} renderTile={renderTile} renderPiece={renderPiece} />
      ) : (
        <p>Loading board...</p>
      )}
    </div>
  );
};

// Generic Tile Component
const Tile = ({ tileData, renderTile, renderPiece }) => {
  return (
    <div className="tile">
      {renderTile(tileData)}
      {tileData.pieces &&
        tileData.pieces.map((piece, index) => renderPiece(piece, index))}
    </div>
  );
};

export default GameBoard;