import React from 'react';

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