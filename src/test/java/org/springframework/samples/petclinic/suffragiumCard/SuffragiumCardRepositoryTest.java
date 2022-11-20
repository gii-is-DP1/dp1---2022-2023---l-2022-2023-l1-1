package org.springframework.samples.petclinic.suffragiumCard;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.samples.petclinic.game.Game;
import org.springframework.samples.petclinic.game.GameRepository;

@DataJpaTest
public class SuffragiumCardRepositoryTest {

    @Autowired
    private SuffragiumCardRepository suffragiumCardRepository;

    @Autowired
    private GameRepository gameRepository;

    @Test
    public void testFindSuffragiumCardByGame() {
        Game g = gameRepository.findById(2);
        SuffragiumCard card = suffragiumCardRepository.findSuffragiumCardByGame(g.getId());
        assertNotNull(card);
    }
    
}
