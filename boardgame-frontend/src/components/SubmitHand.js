import React, { useState } from 'react';
import { sendHand } from "../api";

const SubmitHand = () => {
  // List of all Moves in queue before submit, must be an array, as multiple Moves can be queued up
  const [handMoves, setHandMoves] = useState([]);
  // information retreived from the text-input form field.
  const [handInput, setHandInput] = useState("");

  // add one instance of Move into the queue before submitting
  const handleQueueMove = () => {

    // split input data apart to it's separate values
    const values = handInput.split(",");

    //fail if form does not have correct amount of information
    if (values.length !== 3) {
      alert("Incorrect input!");
      return;
    }

    // convert input into correct numeric numbers
    const [handIdx, toX, toY] = values.map((val) => Number(val.trim()));

    //check if input are actual numbers to begin with
    if (isNaN(handIdx) || isNaN(toX) || isNaN(toY)) {
      alert("All input values must be valid numbers.");
      return;
    }

    // insert Move into queue
    const newHandMove = { handIdx, toX, toY };

    // appends existing list of HandMoves with new Move into Queue
    setHandMoves((prevHandMoves) => [...prevHandMoves, newHandMove]);
    // clean input text for new Moves
    setHandInput("");
  };

  //clears entire queue of moves with the press of a button
  const handleRevertMoves = () => {
    setHandMoves([]);
  };

  // sends list to be handled by api
  const handleSubmitMoves = () => {
    sendHand(handMoves).then(response => {
      console.log('Move response:', response);
    })
    .catch(error => {
      console.error('Failed to send move:', error);
    });
  };

  return (
    <div className="SubmitHand">
      <input
        type="text"
        placeholder="Enter your desired move (handposition, column, row)"
        value={handInput}
        onChange={(e) => setHandInput(e.target.value)}
      />
      <button onClick={handleQueueMove}>Queue Move</button>
      <button onClick={handleRevertMoves}>Cancel Queued Moves</button>
      <button onClick={handleSubmitMoves}>Submit Queued Moves</button>
    </div>
  );
};

export default SubmitHand;