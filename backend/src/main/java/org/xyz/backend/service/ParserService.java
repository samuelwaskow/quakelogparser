package org.xyz.backend.service;

import jakarta.annotation.PostConstruct;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.xyz.backend.model.Game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@Data
public class ParserService {

    private final List<Game> games = new ArrayList<>();

    @Value("${app.data}")
    private Resource logFile;

    /**
     * Reads a games.log upon the application initialization anc construct a list of games models
     */
    @PostConstruct
    public void parseLogs(){

        try {
            final BufferedReader reader = new BufferedReader(new InputStreamReader(logFile.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                parseLine(line);
            }
        } catch (IOException e) {
            throw new RuntimeException("The log file can not be read", e);
        }
    }

    /**
     * Parses a single line
     * @param rawLine Dirty line
     */
    private void parseLine(final String rawLine){
        log.info(rawLine);
    }
}
