package org.springframework.samples.petclinic.game;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Disabled;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.samples.petclinic.enums.State;
import org.springframework.samples.petclinic.suffragiumCard.SuffragiumCard;
import org.springframework.samples.petclinic.turn.Turn;

@ExtendWith(MockitoExtension.class)
public class GameServiceTest {
    
    @Mock
    GameRepository repo;

    private Game createGame(String name, Boolean publicGame) {
        Game game = new Game();
        game.setName(name);
        game.setPublicGame(publicGame);
        game.setState(State.STARTING);
        return game;
    }

    public Turn createTurn(Integer votesLoyal, Integer votesTraitor, Integer votesNeutral) {
        Turn turn = new Turn();
        turn.setCurrentTurn(1);
        turn.setVotesLoyal(votesLoyal);
        turn.setVotesTraitor(votesTraitor);
        turn.setVotesNeutral(votesNeutral);
        return turn;
    }

    private SuffragiumCard createSuffragiumCard(Integer loyalsVotes, Integer traitorsVotes, Integer voteLimit) {
        SuffragiumCard card = new SuffragiumCard();
        card.setLoyalsVotes(loyalsVotes);
        card.setTraitorsVotes(traitorsVotes);
        card.setVoteLimit(voteLimit);
        return card;
    }

    @Test
    public void testSaveGameSuccessful() {
        Game game = createGame("Test game", true);
        Turn turn = createTurn(0, 0, 0);
        GameService service = new GameService(repo);
        try {
            service.saveGame(game, turn);
        } catch (Exception e) {
            fail("no exception should be thrown");
        }
    }

 /* 
    @Test
    public void testSaveGameUnsuccessfulDueToName() {
        Game game = createGame("g", true);
        Turn turn = createTurn(0, 0, 0);
        GameService service = new GameService(repo);
        assertThrows(Exception.class, () -> service.saveGame(game, turn));
    }
*/
/* 
    @Test
    public void testStartGameIfNeeded() {
        Game game = createGame("Game to start", true);
        SuffragiumCard suffragiumCard = createSuffragiumCard(0, 0, 15);
        GameService service = new GameService(repo);
        service.startGameIfNeeded(game, suffragiumCard);
        assertTrue(game.getState() == State.IN_PROCESS);
    }*/

}
