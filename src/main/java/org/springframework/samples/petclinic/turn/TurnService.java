package org.springframework.samples.petclinic.turn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.deck.DeckService;
import org.springframework.samples.petclinic.deck.VoteCard.VCType;
import org.springframework.samples.petclinic.enums.CurrentRound;
import org.springframework.samples.petclinic.game.Game;
import org.springframework.samples.petclinic.game.GameRepository;
import org.springframework.samples.petclinic.player.Player;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TurnService {

    @Autowired
    TurnRepository turnRepository;
    
    @Autowired
    GameRepository gameRepository;

    @Autowired
    DeckService deckService;

    @Autowired
    public TurnService (TurnRepository turnRepository, GameRepository gameRepository, DeckService deckService) {
        this.turnRepository = turnRepository;
        this.gameRepository = gameRepository;
        this.deckService = deckService;
    }

    @Transactional(readOnly = true)
    public Turn getTurnByGame (Game game) {
        return turnRepository.findTurnByGame(game);
    }

    @Transactional
    public void save (Turn turn) {
        turnRepository.save(turn);
    }

    @Transactional
    public void updateTurnVotes (Turn turn, VCType voteType) {
        if (voteType == VCType.GREEN) {
			turn.setVotesLoyal(turn.getVotesLoyal() == null ? 1 : turn.getVotesLoyal() + 1); //creo que aparece null como predeterminado pq esta en la base de dato, si no, no deberia
		}
		if (voteType == VCType.RED) {
			turn.setVotesTraitor(turn.getVotesTraitor() == null ? 1 : turn.getVotesTraitor() + 1);
		}
		turnRepository.save(turn);
    }

    @Transactional
    public void newTurn (Turn turn) {
        Game game = turnRepository.findGameByTurn(turn); //COJO EL GAME ACTUAL
         
        turn.setCurrentTurn(turn.getCurrentTurn() + 1);
        turn.setVotesLoyal(0);
        turn.setVotesTraitor(0);
        turn.setVotesNeutral(0);
        if (turn.getCurrentTurn() > game.getNumPlayers()) {
            turn.setCurrentTurn(1);
            if(game.getRound() == CurrentRound.FIRST) {
                game.setRound(CurrentRound.SECOND);
                gameRepository.save(game);
            }
        }
        turnRepository.save(turn);  
    }

    @Transactional
    public void pretorVoteChange (VCType actualVoteType, VCType changedVoteType, Game game) {
        Turn actualTurn = turnRepository.findTurnByGame(game);
        Integer actualLoyalVotes = actualTurn.getVotesLoyal();
        Integer actualTraitorVotes = actualTurn.getVotesTraitor();
        if (actualVoteType != changedVoteType) {
            
            if (actualVoteType == VCType.GREEN) {
                actualTurn.setVotesLoyal(actualLoyalVotes-1);
                actualTurn.setVotesTraitor(actualTraitorVotes+1);
            }
            else if (actualVoteType == VCType.RED) {
                actualTurn.setVotesTraitor(actualTraitorVotes - 1);
                actualTurn.setVotesLoyal(actualLoyalVotes + 1);
            }
            }
            turnRepository.save(actualTurn);
        }

}