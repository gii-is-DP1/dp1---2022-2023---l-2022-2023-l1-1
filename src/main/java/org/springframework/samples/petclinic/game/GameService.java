package org.springframework.samples.petclinic.game;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.enums.State;
import org.springframework.samples.petclinic.player.Player;
import org.springframework.samples.petclinic.playerInfo.PlayerInfo;
import org.springframework.samples.petclinic.playerInfo.PlayerInfoRepository;
import org.springframework.samples.petclinic.playerInfo.PlayerInfoService;
import org.springframework.samples.petclinic.suffragiumCard.SuffragiumCard;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GameService {
    
    @Autowired
    private GameRepository repo;

    @Autowired
    private PlayerInfoRepository pIRepo;

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
    public Game saveGame(Game game) throws DataAccessException {
        game.setState(State.STARTING);
        game.setNumPlayers(1);
        LocalDate date = LocalDate.now();
        game.setDate(date);
        game.setDuration(0.);
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
