import React, { useEffect, useState } from 'react';
import { fetchHandData, getSpritePath, getGameId } from '../api';
import SubmitHand from './SubmitHand';

const PlayerHand = () => {
  const [handData, setHandData] = useState([]);

  useEffect(() => {
    //
    const fetchData = async () => {
      if (getGameId() != "") {
        try {
          const data = await fetchHandData();
          setHandData(data);
        } catch (error) {
          console.error('Error fetching hand data:', error);
        }
      };
    }
    /*//*
    // Fetch board data when the component mounts
    const fetchData = async () => {
      const gameId = getGameId();
      if (!gameId) return; // Prevents unnecessary fetches when gameId is empty
      const data = await fetchHandData();
      if (data) setHandData(data);
    };
    //*/

    fetchData();

    // Event listener to update board when a move is sent
    //const handleHandUpdate = () => fetchData();
    //document.addEventListener("handUpdated", handleHandUpdate);

    const interval = setInterval(fetchData, 1000); // requests Hand every second (taxing to server)

    return () => clearInterval(interval);
    //return () => document.removeEventListener("handUpdated", handleHandUpdate);
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
      <SubmitHand />
    </div>
  );
};

export default PlayerHand;