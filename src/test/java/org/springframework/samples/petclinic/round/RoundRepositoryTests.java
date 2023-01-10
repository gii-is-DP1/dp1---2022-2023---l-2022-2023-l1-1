package org.springframework.samples.petclinic.round;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.samples.petclinic.game.Game;
import org.springframework.samples.petclinic.game.GameRepository;

@DataJpaTest
public class RoundRepositoryTests {
/* 
    @Autowired
    private RoundRepository roundRepository;

    @Autowired
    private GameRepository gameRepository;

    @Test
    @Disabled
    public void testFindRoundByGame() {
        Game game = gameRepository.findById(2);
        Round round = roundRepository.findRoundByGame(game);
        assertNotNull(game);
        assertNotNull(round);
        assertEquals(round.getId(), 2);
    }*/
    
}
