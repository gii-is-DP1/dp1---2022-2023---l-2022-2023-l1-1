package org.springframework.samples.petclinic.game;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.enums.CurrentRound;
import org.springframework.samples.petclinic.enums.CurrentStage;
import org.springframework.samples.petclinic.enums.State;
import org.springframework.samples.petclinic.suffragiumCard.SuffragiumCard;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GameService {
    
    private GameRepository repo;

    @Autowired
    public GameService(GameRepository repo) {
        this.repo = repo;
    }

    @Transactional(readOnly = true)
    public Game getGameById(Integer id){
        return repo.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Game> getGamesByNameAndState(String name, State s) {
        return repo.findByName(name).stream().filter(g -> g.getState() == s).collect(Collectors.toList());
    }

    @Transactional
    public Game saveGame(Game game) throws DataAccessException {
        game.setState(State.STARTING);
        game.setNumPlayers(1);
        LocalDate date = LocalDate.now();
        game.setDate(date);
        game.setDuration(0.);
        game.setRound(CurrentRound.FIRST);
        game.setTurn(1);
        game.setStage(CurrentStage.VOTING);
        game.setWinners(null);
        game.setSuffragiumCard(null);
        return repo.save(game);
    }

    @Transactional
    public Game startGame(Game game, SuffragiumCard suffragiumCard) throws DataAccessException {
        game.setState(State.IN_PROCESS);
        game.setSuffragiumCard(suffragiumCard);
        return repo.save(game);
    }

    @Transactional
    public void changeStage(Game game, CurrentStage stage) {
        game.setStage(stage);
        repo.save(game);
    }

    @Transactional
    public void changeTurnAndRound(Game game) {
        game.setTurn(game.getTurn() + 1);
        if(game.getTurn() > game.getNumPlayers()) {
            game.setTurn(1);
            if(game.getRound() == CurrentRound.FIRST)
                game.setRound(CurrentRound.SECOND);
        }
        repo.save(game);
    }

    
}
