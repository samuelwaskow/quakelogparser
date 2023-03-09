package org.xyz.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xyz.backend.dto.GameDTO;
import org.xyz.backend.model.Game;
import org.xyz.backend.model.GameKill;
import org.xyz.backend.repository.GameRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public final class GameService {

    @Autowired
    private GameRepository repository;

    /**
     * Persists a list of games
     *
     * @param games models
     */
    public void saveAll(final List<Game> games) {
        repository.saveAll(games);
    }

    /**
     * Find all DTO models
     *
     * @return List of DTOs
     */
    public List<GameDTO> findAll() {
        final List<Game> models = repository.findAll();
        return models.stream()
                .map(this::mapToDTO)
                .toList();
    }

    /**
     * Converts the Game model to a DTO
     *
     * @param game Model
     * @return DTO
     */
    public GameDTO mapToDTO(final Game game) {
        return GameDTO.builder()
                .name(game.getId())
                .totalKills(game.getTotalKills())
                .players(game.getKills().stream()
                        .map(GameKill::getPlayer)
                        .toList())
                .kills(game.getKills().stream()
                        .collect(Collectors.toMap(GameKill::getPlayer, GameKill::getKills)))
                .build();
    }
}
