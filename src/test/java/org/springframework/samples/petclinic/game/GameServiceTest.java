package org.springframework.samples.petclinic.game;


import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.security.Provider.Service;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.enums.State;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class GameServiceTest {
    @Autowired
    private GameService gameService;
/*
    @Test
    public void testGetGamesByNameAndState(){
        List<Game> g = gameService.getGamesByNameAndState("Mi primera partida", State.STARTING);
        List<Game> g2= gameService.getGamesByNameAndState("Partida rapida", State.IN_PROCESS);
        assertNotNull(g);
        assertNotNull(g2);
        assertNotEquals(g2, g);
    }
    

    @Test
    public void testGetGameById(){
        Game g=gameService.getGameById(1);
        Game g2=gameService.getGameById(2);
        assertNotNull(g);
        assertNotNull(g2);
        assertNotEquals(g2, g);
    }
    */
}
