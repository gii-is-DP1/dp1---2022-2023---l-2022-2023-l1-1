package org.springframework.samples.petclinic.deck;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.samples.petclinic.game.Game;
import org.springframework.samples.petclinic.game.GameRepository;
import org.springframework.samples.petclinic.player.Player;
import org.springframework.samples.petclinic.player.PlayerRepository;

@DataJpaTest
public class DeckRepositoryTest {

    @Autowired
    private DeckRepository deckRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private GameRepository gameRepository;

    @Test
    public void testFindPlayerDecks() {
        List<Deck> decks = deckRepository.findPlayerDecks(3);
        assertNotNull(decks);
        assertFalse(decks.isEmpty());
    }

    @Test
    public void testFindPlayerDecksNotExistingDecks() {
        List<Deck> decks = deckRepository.findPlayerDecks(9);
        assertNotNull(decks);
        assertTrue(decks.isEmpty());
    }

    @Test
    public void testFindDecksByPlayerAndGame() {
        Player player = playerRepository.findPlayerByUsername("migmanalv");
        Game game = gameRepository.findById(2);
        List<Deck> decks = deckRepository.findDecksByPlayerAndGame(player, game);
        assertNotNull(decks);
        assertFalse(decks.isEmpty());
    }

    @Test
    public void testFindDecksByPlayerAndGameNotExistingDecks() {
        Player player = playerRepository.findPlayerByUsername("player5");
        Game game = gameRepository.findById(2);
        List<Deck> decks = deckRepository.findDecksByPlayerAndGame(player, game);
        assertNotNull(decks);
        assertTrue(decks.isEmpty());
    }
    
}
