package org.xyz.backend.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

@SpringBootTest
public class ParserServiceTest {

    @Autowired
    private ParserService service;

    @Test
    void shouldFillGamesList() {

        service.parseLogs();
        Assert.notEmpty(service.getGames().toArray(), "Array is empty");
    }
}
