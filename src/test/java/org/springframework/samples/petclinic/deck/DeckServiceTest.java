package org.springframework.samples.petclinic.deck;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.enums.State;
import org.springframework.samples.petclinic.game.Game;
import org.springframework.samples.petclinic.game.GameService;
import org.springframework.samples.petclinic.player.Player;
import org.springframework.samples.petclinic.playerInfo.PlayerInfo;
import org.springframework.samples.petclinic.playerInfo.PlayerInfoService;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class DeckServiceTest {

    @Autowired
    DeckService deckService;

    @Autowired
    GameService gameService;

    @Autowired
    PlayerInfoService playerInfoService;

    @Test
    public void testAssingDecksIfNeeded() {
        Game game = gameService.getGameById(5);
        List<Player> players = playerInfoService.getPlayersByGame(game);
        deckService.assingDecksIfNeeded(game);
        assertFalse(deckService.getDeckByPlayerAndGame(players.get(0), game) == null);

    }
    
}
