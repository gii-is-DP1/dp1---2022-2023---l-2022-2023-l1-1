package org.springframework.samples.petclinic.game;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Disabled;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.samples.petclinic.suffragiumCard.SuffragiumCard;

@ExtendWith(MockitoExtension.class)
public class GameServiceTest {
    
    @Mock
    GameRepository repo;

    private Game createGame(String name, Boolean publicGame) {
        Game game = new Game();
        game.setName(name);
        game.setPublicGame(publicGame);
        return game;
    }

    private SuffragiumCard createSuffragiumCard(Integer loyalsVotes, Integer traitorsVotes, Integer voteLimit) {
        SuffragiumCard card = new SuffragiumCard();
        card.setLoyalsVotes(loyalsVotes);
        card.setTraitorsVotes(traitorsVotes);
        card.setVoteLimit(voteLimit);
        return card;
    }

    @Test
    @Disabled
    public void testSaveGameSuccessful() {
        Game game = createGame("Test game", true);
        SuffragiumCard card = createSuffragiumCard(0, 0, 15);
        GameService service = new GameService(repo);
        try {
            //service.saveGame(game, card);
        } catch (Exception e) {
            fail("no exception should be thrown");
        }
    }
/*
    @Test
    public void testSaveGameUnsuccessfulDueToName() {
        Game game = createGame("g", true);
        SuffragiumCard card = createSuffragiumCard(0, 0, 15);
        GameService service = new GameService(repo);
        assertThrows(Exception.class, () -> service.saveGame(game, card));
    }
*/
}
