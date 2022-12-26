package org.springframework.samples.petclinic.player;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class PlayerRepositoryTest {

    @Autowired
    private PlayerRepository playerRepository;

    @Test
    public void testFindPlayerByUsername() {
        Player player = playerRepository.findPlayerByUsername("player1");
        assertNotNull(player);
    }

    @Test
    public void testFindPlayerByUsernameNotExistingPlayer() {
        Player player = playerRepository.findPlayerByUsername("player99");
        assertNull(player);
    }
    
}
