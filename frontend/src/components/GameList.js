import React from 'react';

function GameList({ games }) {
  return (
    <div>
      <h2>Game List</h2>
      <div className="game-list">
        {Object.keys(games).map((key) => (
          <div className="game-item" key={key}>
            <h3>{key}</h3>
            <p>Players</p>
            <ul>
              {Object.keys(games[key].kills).map((p, k) => (
                <li key={p}>
                  {p} - {k} kills
                </li>
              ))}
            </ul>
            <p>Total Kills: {games[key].total_kills}</p>
          </div>
        ))}
      </div>
    </div>
  );
}

export default GameList;