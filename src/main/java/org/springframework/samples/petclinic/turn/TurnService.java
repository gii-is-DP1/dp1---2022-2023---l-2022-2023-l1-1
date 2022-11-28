package org.springframework.samples.petclinic.turn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.enums.CurrentRound;
import org.springframework.samples.petclinic.game.Game;
import org.springframework.samples.petclinic.round.Round;
import org.springframework.samples.petclinic.round.RoundRepository;
import org.springframework.stereotype.Service;

@Service
public class TurnService {
    TurnRepository turnRepository;
    RoundRepository roundRepository;

    @Autowired
    public TurnService (TurnRepository turnRepository, RoundRepository roundRepository) {
        this.turnRepository = turnRepository;
        this.roundRepository = roundRepository;
    }

    public Turn getTurnByRound (Round round) {
        return turnRepository.findTurnByRound(round);
    }

    public void save (Turn turn) {
        turnRepository.save(turn);

    }

    public void newTurn (Turn turn) {
         Game game = turn.getRound().getGame(); //COJO EL GAME ACTUAL
         Round round = turn.getRound(); //COJO LA RONDA ACTUAL
         System.out.println("aqui" + turn.getCurrentTurn() + game.getNumPlayers());
        turn.setCurrentTurn(turn.getCurrentTurn() + 1);
        System.out.println("aqui" + turn.getCurrentTurn() + game.getNumPlayers());
        if (turn.getCurrentTurn() > game.getNumPlayers()) {
            turn.setCurrentTurn(1);
            System.out.println("aqui" + turn.getCurrentTurn() + game.getNumPlayers());
            if (round.getCurrentRound() == CurrentRound.FIRST) {
                round.setCurrentRound(CurrentRound.SECOND);
                roundRepository.save(round);

            }
        }
        turnRepository.save(turn);
        
    }

}