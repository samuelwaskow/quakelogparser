package org.xyz.backend.service;

import jakarta.annotation.PostConstruct;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.xyz.backend.dto.GameDTO;
import org.xyz.backend.model.Game;
import org.xyz.backend.model.GameKill;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

@Service
@Data
@Slf4j
public final class ParserService {

    private static final String INIT_GAME = "InitGame";
    private static final String END_GAME = "ShutdownGame";
    private static final String TARGET = "killed";
    private static final String NOT_A_PLAYER = "<world>";
    private final Map<String, GameDTO> games = new HashMap<>();
    private final List<Game> models = new ArrayList<>();

    private Game currentGame;

    @Value("${app.data}")
    private Resource logFile;

    @Autowired
    private AccessService accessService;

    /**
     * Reads a games.log upon the application initialization anc construct a list of games models
     */
    @PostConstruct
    public void parseLogs() {

        try {
            final BufferedReader reader = new BufferedReader(new InputStreamReader(logFile.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                parseLine(line);
            }
            accessService.saveAll(models);
        } catch (IOException e) {
            throw new RuntimeException("The log file can not be read", e);
        }
    }

    /**
     * Parses a single line
     *
     * @param rawLine Dirty line
     */
    public void parseLine(final String rawLine) {
        if (rawLine.contains(INIT_GAME)) {
            newGame();
        } else if (rawLine.contains(END_GAME)) {
            endGame();
        } else if (rawLine.contains(TARGET)) {
            genStats(rawLine);
        }
    }

    /**
     * Resets the parser for testing purposes
     */
    public void resetParser() {
        this.games.clear();
        currentGame = null;
    }

    /**
     * Starts to collect data to a new game
     */
    private void newGame() {

        if (currentGame != null) {
            store();
        }
        currentGame = new Game("game_" + (games.size() + 1));
    }

    /**
     * Ends the game data collection
     */
    private void endGame() {
        if (currentGame != null) {
            store();
            currentGame = null;
        }
    }

    /**
     * Stores and converts the model
     */
    private void store() {
        games.put(currentGame.getId(), accessService.mapToDTO(currentGame));
        models.add(currentGame);
    }

    /**
     * Count the number of kills
     */
    private void genStats(final String rawLine) {

        final int targetIndex = rawLine.indexOf(TARGET);
        final String whoKill = rawLine.substring(rawLine.lastIndexOf(':') + 1, targetIndex).trim();
        final String whoDied = rawLine.substring(targetIndex + TARGET.length(), rawLine.indexOf("by")).trim();

        currentGame.addTotalKills();

        if (whoKill.equals(NOT_A_PLAYER) || whoKill.equals(whoDied)) {
            getStatsNotAPlayer(whoDied);
        } else {
            getStatsRegularPlayer(whoKill);
        }
    }


    /**
     * Generate the stats for "world" kills
     *
     * @param whoDied Regular player
     */
    private void getStatsNotAPlayer(final String whoDied) {

        final Optional<GameKill> killOpt = getPlayerKills(whoDied);

        if (killOpt.isPresent()) {
            final GameKill gk = killOpt.get();
            gk.decreaseKills();
        } else {
            currentGame.getKills().add(new GameKill(whoDied, -1));
        }
    }

    /**
     * Generate the stats for Player kills
     *
     * @param whoKill Regular Player
     */
    private void getStatsRegularPlayer(final String whoKill) {

        final Optional<GameKill> killOpt = getPlayerKills(whoKill);

        if (killOpt.isPresent()) {
            final GameKill gk = killOpt.get();
            gk.increaseKills();
        } else {
            currentGame.getKills().add(new GameKill(whoKill, 1));
        }
    }

    /**
     * Search for game kills given a subject
     *
     * @param who Player or not a player
     * @return Optional containing the Game kill
     */
    private Optional<GameKill> getPlayerKills(final String who) {
        return currentGame.getKills().stream()
                .filter(gk -> who.equals(gk.getPlayer()))
                .findFirst();
    }
}
