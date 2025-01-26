import axios from 'axios';

const API_BASE_URL = 'http://localhost:8080';  // Adjust if needed

let gameId = "";
let playerId = null;

export const setPlayerId = (id) => {
  playerId = id;
}

export const getPlayerId = () => playerId;

export const getGameId = () => gameId;

export const fetchGameData = async () => {
  try {
    const response = await axios.get(`${API_BASE_URL}/api/games`);
    return response.data;
  } catch (error) {
    console.error('Error fetching game data:', error);
    return null;
  }
};

// Fetch the current game board
export const fetchBoard = async () => {
  if (gameId === "") return;
  try {
    const response = await axios.get(`${API_BASE_URL}/${gameId}/board`);
    // console.log('Response:', response);  // Log the entire response for debugging
    if (response.status !== 200) {
      throw new Error(`Unexpected response status: ${response.status}`);
    }
    if (!response.data) {
      throw new Error('Response data is empty');
    }
    return response.data;  // Assuming the server returns JSON data
  } catch (error) {
    console.error('Error fetching board data:', error);
    return null;
  }
};

export const fetchHandData = async () => {
  try {
    const response = await axios.post(`${API_BASE_URL}/${gameId}/get-hand`, { playerId: playerId });
    return response.data;
  } catch (error) {
    console.error('Error fetching hand data:', error);
    throw error;
  }
};

// Handle player joining
export const joinGame = async (gameName) => {
  try {
    const response = await axios.get(`${API_BASE_URL}/join`, { params: { game: gameName } });
    gameId = gameName;
    playerId = response.data;
    return response.data;
  } catch (error) {
    console.error('Error joining the game:', error);
    return null;
  }
};

// Send a move to the server
export const sendMove = async (moveData) => {
  try {
    const response = await axios.post(`${API_BASE_URL}/${gameId}/move`, moveData);
    return response.data;
  } catch (error) {
    console.error('Error sending move:', error);
    return null;
  }
};

// Send a hand-based move to the server
export const sendHand = async (handData) => {
  // constructs request skeleton
  const submitData = {
    playerId: playerId,
    moves: handData
  };

  try {
    const response = await axios.post(`${API_BASE_URL}/${gameId}/play-hand`, submitData);
    return response.data;
  } catch (error) {
    console.error('Error sending move:', error);
    return null;
  }
}

// Get path to a sprite
export const getSpritePath = (sprite) => `${API_BASE_URL}/res/${sprite}`;