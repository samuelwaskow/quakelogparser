package org.xyz.backend.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.xyz.backend.dto.GameDTO;
import org.xyz.backend.model.Game;
import org.xyz.backend.model.GameKill;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
public class AccessServiceTest {

    @Autowired
    private AccessService service;

    @Autowired
    private ObjectMapper mapper;

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

    @Test
    void shouldMapModelToDTO() throws JsonProcessingException {

        final Game model = new Game("game_1");
        model.setTotalKills(45);
        model.getKills().add(new GameKill("Dono da Bola", 5));
        model.getKills().add(new GameKill("Isgalamido", 18));
        model.getKills().add(new GameKill("Zeh", 20));

        final GameDTO dto = service.mapToDTO(model);
        final Map<String, GameDTO> map = new HashMap<>();
        map.put("game_1", dto);

        final String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(map);

        final String expected = """
                {
                  "game_1" : {
                    "total_kills" : 45,
                    "players" : [ "Dono da Bola", "Isgalamido", "Zeh" ],
                    "kills" : {
                      "Dono da Bola" : 5,
                      "Isgalamido" : 18,
                      "Zeh" : 20
                    }
                  }
                }""";
        assertEquals(expected, json);

    }

}