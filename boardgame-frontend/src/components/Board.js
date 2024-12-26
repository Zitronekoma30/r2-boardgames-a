import React, {useState } from 'react';
import { getPlayerId, sendMove } from '../api';

let selectedTile = null;

function onTileClick(tileData) {
  console.log('Tile clicked:', tileData);
  if (selectedTile == null) {
    selectedTile = tileData;
  } else {
    const moveData = {
      fromX: selectedTile.x,
      fromY: selectedTile.y,
      toX: tileData.x,
      toY: tileData.y,
      playerId: getPlayerId(),
    };
    selectedTile = null;
    console.log('Sending move:', moveData);
    sendMove(moveData).then(response => {
      console.log('Move response:', response);
    });
  }  
}

// Generic Tile Component
const Tile = ({ tileData, renderTile, renderPiece }) => {
  return (
    <div className="tile" onClick={() => onTileClick(tileData)}>
      {renderTile(tileData)}
      {tileData.pieces &&
        tileData.pieces.map((piece, index) => renderPiece(piece, index))}
    </div>
  );
};

// Generic Board Component
const Board = ({ boardData, renderTile, renderPiece }) => {

  return (
    <div className="board">
      {boardData.map((row, rowIndex) => (
        <div key={rowIndex} className="row">
          {row.map((tile, tileIndex) => (
            <Tile
              key={`${rowIndex}-${tileIndex}`}
              tileData={tile}
              renderTile={renderTile}
              renderPiece={renderPiece}
            />
          ))}
        </div>
      ))}
    </div>
  );
};

export default Board;