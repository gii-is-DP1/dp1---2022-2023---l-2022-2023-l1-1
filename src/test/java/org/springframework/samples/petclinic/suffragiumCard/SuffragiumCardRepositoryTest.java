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

    @Test
    public void testFindSuffragiumCardByGame() {
        SuffragiumCard suffragiumCard = suffragiumCardRepository.findSuffragiumCardByGame(2);
        assertNotNull(suffragiumCard);
    }

    @Test
    public void testFindGameBySuffragiumCard() {
        SuffragiumCard suffragiumCard = suffragiumCardRepository.findById(2).get();
        Game game = suffragiumCardRepository.findGameBySuffragiumCard(suffragiumCard);
        assertNotNull(game);
    }
    
}
