import axios from 'axios';

const API_BASE_URL = 'http://localhost:8080';  // Adjust if needed

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
  try {
    const response = await axios.get(`${API_BASE_URL}/board`);
    return response.data;  // Assuming the server returns JSON data
  } catch (error) {
    console.error('Error fetching board data:', error);
    return null;
  }
};

// Handle player joining
export const joinGame = async (playerName) => {
  try {
    const response = await axios.get(`${API_BASE_URL}/join`, { params: { player: playerName } });
    return response.data;
  } catch (error) {
    console.error('Error joining the game:', error);
    return null;
  }
};

// Send a move to the server
export const sendMove = async (moveData) => {
  try {
    const response = await axios.post(`${API_BASE_URL}/move`, moveData);
    return response.data;
  } catch (error) {
    console.error('Error sending move:', error);
    return null;
  }
};