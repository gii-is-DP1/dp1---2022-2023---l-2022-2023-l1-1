package org.springframework.samples.petclinic.turn;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.samples.petclinic.deck.VoteCard.VCType;
import org.springframework.samples.petclinic.enums.CurrentRound;
import org.springframework.samples.petclinic.enums.CurrentStage;
import org.springframework.samples.petclinic.enums.State;
import org.springframework.samples.petclinic.game.Game;
import org.springframework.samples.petclinic.game.GameRepository;
import org.springframework.samples.petclinic.game.GameService;

@ExtendWith(MockitoExtension.class)
public class TurnServiceTest {

    @Mock
    TurnRepository turnRepository;

    @Mock
    GameRepository gameRepository;

    private Turn createTurn(Integer currentTurn, Integer votesLoyal, Integer votesTraitor, Integer votesNeutral) {
        Turn turn = new Turn();
        turn.setCurrentTurn(currentTurn);
        turn.setVotesLoyal(votesLoyal);
        turn.setVotesTraitor(votesTraitor);
        turn.setVotesNeutral(votesNeutral);
        return turn;
    }

    private Game createGame(String name, Boolean publicGame, Turn turn, Integer numPlayers) {
        Game game = new Game();
        game.setName(name);
        game.setPublicGame(publicGame);
        game.setState(State.IN_PROCESS);
        game.setNumPlayers(numPlayers);
        game.setRound(CurrentRound.FIRST);
        game.setTurn(turn);
        return game;
    }

    @Test
    public void testSaveGameSuccessful() {
        Turn turn = createTurn(1, 0, 0, 0);
        TurnService turnService = new TurnService(turnRepository);
        try {
            turnService.save(turn);
        } catch (Exception e) {
            fail("no exception should be thrown");
        }
    }

    @Test
    public void testUpdateTurnVotes () { //funciona
        Turn turn = createTurn(1, 0, 0, 0);
        Integer originalLoyalVotes = turn.getVotesLoyal();
        Integer originalTraitorVotes = turn.getVotesTraitor();
        Integer originalNeutralVotes = turn.getVotesNeutral();
        TurnService turnService = new TurnService(turnRepository);

        turnService.updateTurnVotes(turn, VCType.YELLOW);
        assertTrue(turn.getVotesLoyal() == originalLoyalVotes);
        assertTrue(turn.getVotesTraitor() == originalTraitorVotes);
        assertTrue(turn.getVotesNeutral() == originalNeutralVotes + 1);

        turnService.updateTurnVotes(turn, VCType.GREEN);
        assertTrue(turn.getVotesLoyal() == originalLoyalVotes + 1);
        assertTrue(turn.getVotesTraitor() == originalTraitorVotes);
        assertTrue(turn.getVotesNeutral() == originalNeutralVotes + 1);

        turnService.updateTurnVotes(turn, VCType.RED);
        assertTrue(turn.getVotesLoyal() == originalLoyalVotes + 1);
        assertTrue(turn.getVotesTraitor() == originalTraitorVotes + 1);
        assertTrue(turn.getVotesNeutral() == originalNeutralVotes + 1);        
    }

    @Test
    public void testNewTurn() { //no sale
        Turn turn = createTurn(3, 0, 0, 0);

        Game game = createGame("Testing new turn", true, turn, 5);

        TurnService turnService = new TurnService(turnRepository);
        turnService.newTurn(turn);

    }

    @Test
    public void testPretorVoteChange() { //funciona
        Turn turn = createTurn(1, 1, 1, 0);
        Game game = createGame("Testing new turn", true, turn, 5);
        Integer originalLoyalVotes = turn.getVotesLoyal();
        Integer originalTraitorVotes = turn.getVotesTraitor();
        TurnService turnService = new TurnService(turnRepository);

        turnService.pretorVoteChange(VCType.GREEN, VCType.GREEN, game);
        assertTrue(originalLoyalVotes == game.getTurn().getVotesLoyal());
        assertTrue(originalTraitorVotes == game.getTurn().getVotesTraitor());

        turnService.pretorVoteChange(VCType.RED, VCType.RED, game);
        assertTrue(originalLoyalVotes == game.getTurn().getVotesLoyal());
        assertTrue(originalTraitorVotes == game.getTurn().getVotesTraitor());

        turnService.pretorVoteChange(VCType.RED, VCType.GREEN, game);
        assertTrue(originalLoyalVotes == game.getTurn().getVotesLoyal() - 1);
        assertTrue(originalTraitorVotes == game.getTurn().getVotesTraitor() + 1);

        turn = createTurn(1, 1, 1, 0);
        game = createGame("Testing new turn", true, turn, 5);

        turnService.pretorVoteChange(VCType.GREEN, VCType.RED, game);
        assertTrue(originalLoyalVotes == game.getTurn().getVotesLoyal() + 1);
        assertTrue(originalTraitorVotes == game.getTurn().getVotesTraitor() - 1);

    }
    
}
