import React, { useState, useEffect } from 'react';
import GameList from './components/GameList';
import PlayerRanking from './components/PlayerRanking';
import './App.css';

function App() {
  const [games, setGames] = useState([]);

  useEffect(() => {
    fetch('http://localhost:8080/games')
      .then(response => response.json())
      .then(data => setGames(data))
      .catch(error => console.error(error));
  }, []);

  return (
    <div>
      <h1>Quake Log Parser</h1>
      <PlayerRanking games={games} />
      <GameList games={games} />
    </div>
  );
}

export default App;
