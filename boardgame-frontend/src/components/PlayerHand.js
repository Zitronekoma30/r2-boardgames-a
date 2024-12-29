import React, { useEffect, useState } from 'react';
import { fetchHandData, getSpritePath } from '../api';

const PlayerHand = () => {
  const [handData, setHandData] = useState([]);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const data = await fetchHandData();
        setHandData(data);
      } catch (error) {
        console.error('Error fetching hand data:', error);
      }
    };

    fetchData();
    const interval = setInterval(fetchData, 1000);

    return () => clearInterval(interval);
  }, []);

  const renderHandPiece = (piece, index) => (
    <img
      key={index}
      src={`${getSpritePath(piece.sprite)}`}
      alt={piece.pieceName}
      className="hand-piece"
    />
  );

  return (
    <div className="PlayerHand">
      <h2>Your Hand</h2>
      <div className="hand">
        {handData.map((piece, index) => renderHandPiece(piece, index))}
      </div>
    </div>
  );
};

export default PlayerHand;