package org.xyz.backend.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.xyz.backend.service.ParserService;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public final class GameControllerTest {

    @Autowired
    private ParserService service;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetAllDTOs() throws Exception {
        service.parseLogs();

        mockMvc.perform(get("/games"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(42)))
                .andExpect(jsonPath("$[0].total_kills", is(0)))
                .andExpect(jsonPath("$[1].total_kills", is(11)))
                .andExpect(jsonPath("$[2].total_kills", is(4)))
                .andExpect(jsonPath("$[2].players", hasItems("Isgalamido", "Zeh", "Dono da Bola")))
                .andExpect(jsonPath("$[2].kills", hasEntry("Dono da Bola", -1)))
                .andExpect(jsonPath("$[2].kills", hasEntry("Isgalamido", 1)))
                .andExpect(jsonPath("$[2].kills", hasEntry("Zeh", -2))
                );
    }

}
