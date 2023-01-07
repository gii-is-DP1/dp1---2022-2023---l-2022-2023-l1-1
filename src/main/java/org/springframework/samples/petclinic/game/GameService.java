package org.springframework.samples.petclinic.game;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.enums.CurrentRound;
import org.springframework.samples.petclinic.enums.CurrentStage;
import org.springframework.samples.petclinic.enums.State;
import org.springframework.samples.petclinic.player.Player;
import org.springframework.samples.petclinic.playerInfo.PlayerInfo;
import org.springframework.samples.petclinic.playerInfo.PlayerInfoRepository;
import org.springframework.samples.petclinic.playerInfo.PlayerInfoService;
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
    private PlayerInfoRepository pIRepo;
    private TurnRepository turnRepository;

    @Autowired
    public GameService(GameRepository repo) {
        this.repo = repo;
    }

    @Transactional(readOnly = true)
    public List<Game> getPageablesGames(Pageable pageable){
        return repo.findAllPageable(pageable);
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
    public Game saveGame(Game game, Turn turn) throws DataAccessException {
        game.setState(State.STARTING);
        game.setNumPlayers(1);
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
    public Game startGame(Game game, SuffragiumCard suffragiumCard) throws DataAccessException {
        game.setState(State.IN_PROCESS);
        game.setSuffragiumCard(suffragiumCard);
        return repo.save(game);
    }

    @Transactional
    public void joinGame(Game game, Player p) throws DataAccessException {
        List<PlayerInfo> playersInLobby = pIRepo.findPlayerInfosByGame(game);
        for(PlayerInfo pI : playersInLobby){
            if(pI.getPlayer().getId()!=p.getId()){
                game.setNumPlayers(game.getNumPlayers()+1);
            }
        }
        repo.save(game);
    public void joinGame(Game game) throws DataAccessException {
        game.setNumPlayers(game.getNumPlayers()+1);
        repo.save(game);
    }
    
    @Transactional
    public void changeStage(Game game, CurrentStage stage) {
        game.setStage(stage);
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

    @Transactional
    public void changeTurnAndRound(Game game) {
        Turn turnToChange = game.getTurn();
        turnToChange.setCurrentTurn(turnToChange.getCurrentTurn() + 1);
        turnToChange.setVotesLoyal(0);
        turnToChange.setVotesTraitor(0);
        turnToChange.setVotesNeutral(0);
        turnRepository.save(turnToChange);
        if(game.getTurn().getCurrentTurn() > game.getNumPlayers()) {
            turnToChange.setCurrentTurn(1);
            turnRepository.save(turnToChange);
            if(game.getRound() == CurrentRound.FIRST)
                game.setRound(CurrentRound.SECOND);
        }
        repo.save(game);
    }
    
    @Transactional
    public void leaveGame(Game game, Player p) throws DataAccessException {
        List<PlayerInfo> playersInLobby = pIRepo.findPlayerInfosByGame(game);
        for(PlayerInfo pI : playersInLobby){
            if(pI.getPlayer().getId()==p.getId());
            pIRepo.deleteById(pI.getId().longValue());
            game.setNumPlayers(game.getNumPlayers()-1);
        }
        repo.save(game);
    }

    @Transactional 
    public void spectatorLeaveGame(Game game, Player player, PlayerInfo spectatorPlayerInfo) throws DataAccessException{
        //List<PlayerInfo> playersInLobby = pIRepo.findPlayerInfosByGame(game);
        if(spectatorPlayerInfo.getSpectator()==true){
            // playersInLobby.remove(player); DELETE
            pIRepo.deleteById(spectatorPlayerInfo.getId().longValue());
        }
        repo.save(game);
    }
}
