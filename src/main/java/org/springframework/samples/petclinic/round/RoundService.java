package org.springframework.samples.petclinic.round;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.game.Game;
import org.springframework.stereotype.Service;

@Service
public class RoundService {
    RoundRepository roundRepository;

    @Autowired
    public RoundService (RoundRepository roundRepository) {
        this.roundRepository = roundRepository;
    }

    public Round getRoundByGame (Game game) {
        return roundRepository.findRoundByGame(game);
    }

    public void save (Round round) {
        roundRepository.save(round);
    }
    
}
