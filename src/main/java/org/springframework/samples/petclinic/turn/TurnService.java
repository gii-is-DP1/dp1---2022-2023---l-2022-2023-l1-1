package org.springframework.samples.petclinic.turn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.deck.DeckService;
import org.springframework.samples.petclinic.deck.VoteCard.VCType;
import org.springframework.samples.petclinic.enums.CurrentRound;
import org.springframework.samples.petclinic.game.Game;
import org.springframework.samples.petclinic.player.Player;
import org.springframework.samples.petclinic.round.Round;
import org.springframework.samples.petclinic.round.RoundRepository;
import org.springframework.stereotype.Service;

@Service
public class TurnService {
    TurnRepository turnRepository;
    RoundRepository roundRepository;
    DeckService deckService;

    @Autowired
    public TurnService (TurnRepository turnRepository, RoundRepository roundRepository, DeckService deckService) {
        this.turnRepository = turnRepository;
        this.roundRepository = roundRepository;
        this.deckService = deckService;
    }

    public Turn getTurnByRound (Round round) {
        return turnRepository.findTurnByRound(round);
    }

    public void save (Turn turn) {
        turnRepository.save(turn);

    }


    public void updateTurnVotes (Turn actualTurn, VCType voteType, Player actualPlayer) {
        
        if (voteType == VCType.GREEN) {
			actualTurn.setVotesLoyal(actualTurn.getVotesLoyal() == null ? 1 : actualTurn.getVotesLoyal() + 1); //creo que aparece null como predeterminado pq esta en la base de dato, si no, no deberia
		}
		if (voteType == VCType.RED) {
			actualTurn.setVotesTraitor(actualTurn.getVotesTraitor() == null ? 1 : actualTurn.getVotesTraitor() + 1);
		}
        
		turnRepository.save(actualTurn);
    }

    public void newTurn (Turn turn) {
         Game game = turn.getRound().getGame(); //COJO EL GAME ACTUAL
         Round round = turn.getRound(); //COJO LA RONDA ACTUAL
         
        turn.setCurrentTurn(turn.getCurrentTurn() + 1);
        turn.setVotesLoyal(0);
        turn.setVotesTraitor(0);
        if (turn.getCurrentTurn() > game.getNumPlayers()) {
            turn.setCurrentTurn(1);
            
            if (round.getCurrentRound() == CurrentRound.FIRST) {
                round.setCurrentRound(CurrentRound.SECOND);
                roundRepository.save(round);

            }
        }
        turnRepository.save(turn);
        
    }

    public void pretorVoteChange (VCType actualVoteType, VCType changedVoteType, Game game) {
        Turn actualTurn = turnRepository.findTurnByRound(roundRepository.findRoundByGame(game));
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