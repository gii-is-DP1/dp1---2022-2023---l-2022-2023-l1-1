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
import org.springframework.samples.petclinic.turn.Turn;
import org.springframework.samples.petclinic.turn.TurnRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GameService {
    
    @Autowired
    private GameRepository repo;

    @Autowired
    private TurnRepository turnRepository;

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

    private static final Integer STARTING_NUMBER_OF_PLAYERS = 1;

    @Transactional
    public Game saveGame(Game game, Turn turn) throws DataAccessException {
        game.setState(State.STARTING);
        game.setNumPlayers(STARTING_NUMBER_OF_PLAYERS);
        LocalDate date = LocalDate.now();
        game.setDate(date);
        game.setDuration(0.);
        game.setRound(CurrentRound.FIRST);
        game.setTurn(turn);
        game.setStage(CurrentStage.VOTING);
        game.setWinners(null);
        game.setSuffragiumCard(null);
        return repo.save(game);
    }

    @Transactional
    public Game startGameIfNeeded(Game game, SuffragiumCard suffragiumCard) throws DataAccessException {
        if(game.getState() == State.STARTING) {
            game.setState(State.IN_PROCESS);
            game.setSuffragiumCard(suffragiumCard);
        }
        return repo.save(game);
    }

    @Transactional
    public void joinGame(Game game) throws DataAccessException {
        game.setNumPlayers(game.getNumPlayers()+1);
        repo.save(game);
    }
    
    @Transactional
    public void changeStage(Game game, CurrentStage stage) {
        game.setStage(stage);
        if (stage == CurrentStage.VOTING) {
            changeTurnAndRound(game); //si cambiamos a voting es pq pasamos de turno
        }
        repo.save(game);
    }

    private static final Integer TOTAL_VOTES_NUMBER = 2;

    @Transactional
    public void changeStageIfVotesCompleted(Game game) {
        if(game.getTurn().getVoteCount() == TOTAL_VOTES_NUMBER) {
            game.setStage(CurrentStage.VETO);
            repo.save(game);
        }
    }

    private static final Integer NEW_TURN_INITIAL_VOTES = 0;

    @Transactional
    public void changeTurnAndRound(Game game) {
        Turn turnToChange = game.getTurn();
        turnToChange.setVotesLoyal(NEW_TURN_INITIAL_VOTES);
        turnToChange.setVotesTraitor(NEW_TURN_INITIAL_VOTES);
        turnToChange.setVotesNeutral(NEW_TURN_INITIAL_VOTES);
        turnToChange.setCurrentTurn(turnToChange.getCurrentTurn() + 1);
        if(game.getTurn().getCurrentTurn() > game.getNumPlayers()) {
            turnToChange.setCurrentTurn(1);
            turnRepository.save(turnToChange);
            if(game.getRound() == CurrentRound.FIRST)
                game.setRound(CurrentRound.SECOND);
        }
        turnRepository.save(turnToChange);
        repo.save(game);
    }
    
}
