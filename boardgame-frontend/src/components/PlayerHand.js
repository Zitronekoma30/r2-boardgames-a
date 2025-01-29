import React, { useEffect, useState } from 'react';
import { fetchHandData, getSpritePath, getGameId } from '../api';
import SubmitHand from './SubmitHand';

const PlayerHand = () => {
  const [handData, setHandData] = useState([]);
  const [isHidden, setIsHidden] = useState(true)

  useEffect(() => {
    const fetchData = async () => {
      if (getGameId() != "") {
        try {
          const data = await fetchHandData();
          setHandData(data);
          setIsHidden(false);
        } catch (error) {
          console.error('Error fetching hand data:', error);
        }
      };
    }


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
    <div className={`PlayerHand ${isHidden ? "hide" : ""}`}>
      <h2>Your Hand</h2>
      <div className="hand">
        {handData.map((piece, index) => renderHandPiece(piece, index))}
      </div>
      <SubmitHand />
    </div>
  );
};

export default PlayerHand;
