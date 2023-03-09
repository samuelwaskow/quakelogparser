package org.xyz.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xyz.backend.dto.GameDTO;
import org.xyz.backend.service.GameService;

import java.util.Map;

@RestController
@RequestMapping("games")
public final class GameController {

    @Autowired
    private GameService service;

    @GetMapping
    @CrossOrigin(origins = "http://localhost:3000")
    public Map<String, GameDTO> findAll() {
        return service.findAll();
    }
}
