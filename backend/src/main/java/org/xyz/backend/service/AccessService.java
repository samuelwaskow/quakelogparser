package org.xyz.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xyz.backend.model.Game;
import org.xyz.backend.repository.AccessRepository;

import java.util.List;

@Service
public final class AccessService {

    @Autowired
    private AccessRepository accessRepository;

    /**
     * Persists a list of games
     *
     * @param games models
     */
    public void saveAll(final List<Game> games) {
        accessRepository.saveAll(games);
    }
}
