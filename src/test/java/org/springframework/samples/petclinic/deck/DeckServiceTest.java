package org.springframework.samples.petclinic.deck;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.deck.FactionCard.FCType;
import org.springframework.samples.petclinic.enums.State;
import org.springframework.samples.petclinic.game.Game;
import org.springframework.samples.petclinic.game.GameService;
import org.springframework.samples.petclinic.player.Player;
import org.springframework.samples.petclinic.player.PlayerService;
import org.springframework.samples.petclinic.playerInfo.PlayerInfo;
import org.springframework.samples.petclinic.playerInfo.PlayerInfoService;
import org.springframework.stereotype.Service;

//@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
//@SpringBootTest
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class DeckServiceTest {

    @Mock
    private DeckRepository deckRepository;

    @Mock
    private FactionCardRepository factionCardRepository;
/* 
    @Test
    public void testUpdateFactionDeck() {
        FCType id = FCType.LOYAL;
        FactionCard loyalCard = new FactionCard();
        loyalCard.setType(id);
        when(factionCardRepository.findById(any(FCType.class))).thenReturn(Optional.of(loyalCard));
        Deck deck = new Deck();
        deck.setId(2);
        DeckService service = new DeckService(deckRepository);
        service.updateFactionDeck(deck, id);
        System.out.println("================================="+deck.getFactionCards());
        assertTrue(deck.getFactionCards().contains(loyalCard));
    }*/

   /*  @Test
    public void testAssingDecksIfNeeded() {
        Game game = gameService.getGameById(5);
        List<Player> players = playerInfoService.getPlayersByGame(game);
        deckService.assingDecksIfNeeded(game);
        assertFalse(deckService.getDeckByPlayerAndGame(players.get(0), game) == null);

    }*/
    
}
