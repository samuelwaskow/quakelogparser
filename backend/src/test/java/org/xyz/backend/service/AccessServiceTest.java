package org.xyz.backend.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.xyz.backend.model.Game;
import org.xyz.backend.model.GameKill;

import java.util.ArrayList;
import java.util.List;


@SpringBootTest
public class AccessServiceTest {

    @Autowired
    private AccessService service;

    @Test
    void shouldPersistAllModels() {
        final List<Game> models = new ArrayList<>();

        final Game g1 = new Game("game_1");
        models.add(g1);

        final Game g2 = new Game("game_2");
        g2.getKills().add(new GameKill("Josh", 1));
        g2.setTotalKills(1);
        models.add(g2);

        service.saveAll(models);
    }

}