package org.springframework.samples.petclinic.game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class GameRepositoryTest {

    @Autowired
    private GameRepository gameRepository;

    @Test
    public void testFindByName() {
        List<Game> g = gameRepository.findByName("Mi primera partida");
        assertNotNull(g);
        assertFalse(g.isEmpty());
        assertEquals(1, g.size());
    }

    @Test
    public void testFindById() {
        Game g = gameRepository.findById(1);
        Game g2 = gameRepository.findById(2);
        assertNotNull(g);
        assertNotNull(g2);
        assertNotEquals(g2, g);
    }
}
