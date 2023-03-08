package org.xyz.backend.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;
import org.xyz.backend.dto.GameDTO;
import org.xyz.backend.model.Game;
import org.xyz.backend.model.GameKill;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.util.Assert.notEmpty;

@SpringBootTest
public class ParserServiceTest {

    @Autowired
    private ParserService service;

    @Autowired
    private ObjectMapper mapper;

    @Test
    void shouldLoseKillWhenDiesByWorld() {

        service.resetParser();

        final List<String> lines = new ArrayList<>();
        lines.add("InitGame: \\sv_floodProtect\\1\\sv_maxPing\\0\\sv_minPing\\0\\sv_maxRate\\10000\\sv_minRate\\0\\sv_hostname\\Code Miner Server\\g_gametype\\0\\sv_privateClients\\2\\sv_maxclients\\16\\sv_allowDownload\\0\\dmflags\\0\\fraglimit\\20\\timelimit\\15\\g_maxGameClients\\0\\capturelimit\\8\\version\\ioq3 1.36 linux-x86_64 Apr 12 2009\\protocol\\68\\mapname\\q3dm17\\gamename\\baseq3\\g_needpass\\0");
        lines.add("22:06 Kill: 2 3 7: Isgalamido killed Mocinha by MOD_ROCKET_SPLASH");
        lines.add("3:12 Kill: 2 4 6: Dono da Bola killed Zeh by MOD_ROCKET");
        lines.add("23:06 Kill: 1022 2 22: <world> killed Isgalamido by MOD_TRIGGER_HURT");
        lines.add("3:41 Kill: 2 3 6: Dono da Bola killed Isgalamido by MOD_ROCKET");
        lines.add("3:41 Kill: 2 3 6: Isgalamido killed Isgalamido by MOD_ROCKET");
        lines.add("1:47 ShutdownGame:");
        lines.forEach(l -> service.parseLine(l));

        assertEquals(1, service.getGames().size());

        final GameDTO g = service.getGames().get("game_1");
        assertEquals(2, g.getKills().size());

        final Map<String, Integer> kills = g.getKills();
        Assert.isTrue(kills.containsKey("Isgalamido"), "Key does not exist");
        Assert.isTrue(kills.containsKey("Dono da Bola"), "Key does not exist");

        assertEquals(-1, kills.get("Isgalamido"));
        assertEquals(2, kills.get("Dono da Bola"));

    }

    @Test
    void shouldCheckEndingGameMarks() {

        service.resetParser();

        final List<String> lines = new ArrayList<>();
        lines.add("InitGame: \\sv_floodProtect\\1\\sv_maxPing\\0\\sv_minPing\\0\\sv_maxRate\\10000\\sv_minRate\\0\\sv_hostname\\Code Miner Server\\g_gametype\\0\\sv_privateClients\\2\\sv_maxclients\\16\\sv_allowDownload\\0\\dmflags\\0\\fraglimit\\20\\timelimit\\15\\g_maxGameClients\\0\\capturelimit\\8\\version\\ioq3 1.36 linux-x86_64 Apr 12 2009\\protocol\\68\\mapname\\q3dm17\\gamename\\baseq3\\g_needpass\\0");
        lines.add("InitGame: \\sv_floodProtect\\1\\sv_maxPing\\0\\sv_minPing\\0\\sv_maxRate\\10000\\sv_minRate\\0\\sv_hostname\\Code Miner Server\\g_gametype\\0\\sv_privateClients\\2\\sv_maxclients\\16\\sv_allowDownload\\0\\dmflags\\0\\fraglimit\\20\\timelimit\\15\\g_maxGameClients\\0\\capturelimit\\8\\version\\ioq3 1.36 linux-x86_64 Apr 12 2009\\protocol\\68\\mapname\\q3dm17\\gamename\\baseq3\\g_needpass\\0");
        lines.add("1:47 ShutdownGame:");
        lines.add("InitGame: \\sv_floodProtect\\1\\sv_maxPing\\0\\sv_minPing\\0\\sv_maxRate\\10000\\sv_minRate\\0\\sv_hostname\\Code Miner Server\\g_gametype\\0\\sv_privateClients\\2\\sv_maxclients\\16\\sv_allowDownload\\0\\dmflags\\0\\fraglimit\\20\\timelimit\\15\\g_maxGameClients\\0\\capturelimit\\8\\version\\ioq3 1.36 linux-x86_64 Apr 12 2009\\protocol\\68\\mapname\\q3dm17\\gamename\\baseq3\\g_needpass\\0");
        lines.add("1:47 ShutdownGame:");
        lines.add("InitGame: \\sv_floodProtect\\1\\sv_maxPing\\0\\sv_minPing\\0\\sv_maxRate\\10000\\sv_minRate\\0\\sv_hostname\\Code Miner Server\\g_gametype\\0\\sv_privateClients\\2\\sv_maxclients\\16\\sv_allowDownload\\0\\dmflags\\0\\fraglimit\\20\\timelimit\\15\\g_maxGameClients\\0\\capturelimit\\8\\version\\ioq3 1.36 linux-x86_64 Apr 12 2009\\protocol\\68\\mapname\\q3dm17\\gamename\\baseq3\\g_needpass\\0");
        lines.add("InitGame: \\sv_floodProtect\\1\\sv_maxPing\\0\\sv_minPing\\0\\sv_maxRate\\10000\\sv_minRate\\0\\sv_hostname\\Code Miner Server\\g_gametype\\0\\sv_privateClients\\2\\sv_maxclients\\16\\sv_allowDownload\\0\\dmflags\\0\\fraglimit\\20\\timelimit\\15\\g_maxGameClients\\0\\capturelimit\\8\\version\\ioq3 1.36 linux-x86_64 Apr 12 2009\\protocol\\68\\mapname\\q3dm17\\gamename\\baseq3\\g_needpass\\0");
        lines.add("1:47 ShutdownGame:");
        lines.add("InitGame: \\sv_floodProtect\\1\\sv_maxPing\\0\\sv_minPing\\0\\sv_maxRate\\10000\\sv_minRate\\0\\sv_hostname\\Code Miner Server\\g_gametype\\0\\sv_privateClients\\2\\sv_maxclients\\16\\sv_allowDownload\\0\\dmflags\\0\\fraglimit\\20\\timelimit\\15\\g_maxGameClients\\0\\capturelimit\\8\\version\\ioq3 1.36 linux-x86_64 Apr 12 2009\\protocol\\68\\mapname\\q3dm17\\gamename\\baseq3\\g_needpass\\0");
        lines.add("InitGame: \\sv_floodProtect\\1\\sv_maxPing\\0\\sv_minPing\\0\\sv_maxRate\\10000\\sv_minRate\\0\\sv_hostname\\Code Miner Server\\g_gametype\\0\\sv_privateClients\\2\\sv_maxclients\\16\\sv_allowDownload\\0\\dmflags\\0\\fraglimit\\20\\timelimit\\15\\g_maxGameClients\\0\\capturelimit\\8\\version\\ioq3 1.36 linux-x86_64 Apr 12 2009\\protocol\\68\\mapname\\q3dm17\\gamename\\baseq3\\g_needpass\\0");
        lines.add("1:47 ShutdownGame:");
        lines.forEach(l -> service.parseLine(l));

        assertEquals(7, service.getGames().size());

    }

    @Test
    void shouldNotDisplayWorldAsPlayer() {

        service.resetParser();

        final List<String> lines = new ArrayList<>();
        lines.add("InitGame: \\sv_floodProtect\\1\\sv_maxPing\\0\\sv_minPing\\0\\sv_maxRate\\10000\\sv_minRate\\0\\sv_hostname\\Code Miner Server\\g_gametype\\0\\sv_privateClients\\2\\sv_maxclients\\16\\sv_allowDownload\\0\\dmflags\\0\\fraglimit\\20\\timelimit\\15\\g_maxGameClients\\0\\capturelimit\\8\\version\\ioq3 1.36 linux-x86_64 Apr 12 2009\\protocol\\68\\mapname\\q3dm17\\gamename\\baseq3\\g_needpass\\0");
        lines.add("22:06 Kill: 2 3 7: <world> killed Mocinha by MOD_ROCKET_SPLASH");
        lines.add("3:12 Kill: 2 4 6: <world> killed Zeh by MOD_ROCKET");
        lines.add("23:06 Kill: 1022 2 22: <world> killed Isgalamido by MOD_TRIGGER_HURT");
        lines.add("3:41 Kill: 2 3 6: <world> killed Isgalamido by MOD_ROCKET");
        lines.add("1:47 ShutdownGame:");
        lines.forEach(l -> service.parseLine(l));

        assertEquals(1, service.getGames().size());

        final GameDTO g = service.getGames().get("game_1");
        assertEquals(3, g.getKills().size());
        assertEquals(4, g.getTotalKills());

    }

    @Test
    void shouldComputeTotalKills() {

        service.resetParser();

        final List<String> lines = new ArrayList<>();
        lines.add("InitGame: \\sv_floodProtect\\1\\sv_maxPing\\0\\sv_minPing\\0\\sv_maxRate\\10000\\sv_minRate\\0\\sv_hostname\\Code Miner Server\\g_gametype\\0\\sv_privateClients\\2\\sv_maxclients\\16\\sv_allowDownload\\0\\dmflags\\0\\fraglimit\\20\\timelimit\\15\\g_maxGameClients\\0\\capturelimit\\8\\version\\ioq3 1.36 linux-x86_64 Apr 12 2009\\protocol\\68\\mapname\\q3dm17\\gamename\\baseq3\\g_needpass\\0");
        lines.add("22:06 Kill: 2 3 7: Isgalamido killed Mocinha by MOD_ROCKET_SPLASH");
        lines.add("3:12 Kill: 2 4 6: Dono da Bola killed Zeh by MOD_ROCKET");
        lines.add("23:06 Kill: 1022 2 22: <world> killed Isgalamido by MOD_TRIGGER_HURT");
        lines.add("3:41 Kill: 2 3 6: Dono da Bola killed Isgalamido by MOD_ROCKET");

        lines.add("22:06 Kill: 2 3 7: <world> killed Mocinha by MOD_ROCKET_SPLASH");
        lines.add("3:12 Kill: 2 4 6: <world> killed Zeh by MOD_ROCKET");
        lines.add("23:06 Kill: 1022 2 22: <world> killed Isgalamido by MOD_TRIGGER_HURT");
        lines.add("3:41 Kill: 2 3 6: <world> killed Isgalamido by MOD_ROCKET");
        lines.add("1:47 ShutdownGame:");
        lines.forEach(l -> service.parseLine(l));

        assertEquals(1, service.getGames().size());

        final GameDTO g = service.getGames().get("game_1");
        assertEquals(8, g.getTotalKills());

    }

    @Test
    void shouldFillGamesList() {
        service.parseLogs();
        notEmpty(service.getGames(), "Array is empty");
    }

    @Test
    void shouldTotalKillsEqualsOrGreaterIndividualKills() {
        service.parseLogs();
        for (final GameDTO g : service.getGames().values()) {
            final int individualKills = g.getKills().values().stream().reduce(0, Integer::sum);
            Assert.isTrue(g.getTotalKills() >= individualKills, "Individual kills sum is lower than the total kills");
        }
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
