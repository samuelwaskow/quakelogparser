import React from 'react';

function PlayerRanking({ games }) {
  const players = {};

  Object.keys(games).forEach((key) => {
    Object.keys(games[key].kills).forEach((p, k) => {
      if (players[p]) {
        players[p] += k || 0;
      } else {
        players[p] = k || 0;
      }
    })
  });

  const sortedPlayers = Object.keys(players).sort((a, b) => players[b] - players[a]);

  return (
    <div>
      <h2>Player Ranking</h2>
      <ol>
        {sortedPlayers.map(player => (
          <li key={player}>
            {player} - {players[player]} kills
          </li>
        ))}
      </ol>
    </div>
  );
}

export default PlayerRanking;
