package org.springframework.samples.petclinic.playerInfo;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.game.Game;
import org.springframework.samples.petclinic.player.Player;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PlayerInfoService {

    @Autowired
    private PlayerInfoRepository repo; 

    @Autowired
    public PlayerInfoService(PlayerInfoRepository repo){
        this.repo=repo;
    }
    @Transactional(readOnly = true)
    public List<PlayerInfo> getLobbies() {
        return repo.findAll();
    }

    @Transactional(readOnly = true)
    public Game getPlayerInfoById(Integer id){
        return repo.findById(id);
    }

    @Transactional(readOnly = true)
    public void deletePlayerInfo(Long id) {
        repo.deleteById(id);;
    }

    @Transactional(readOnly = true)
    public List<PlayerInfo> getPlayerInfosByGame(Game game) {
        return repo.findPlayerInfosByGame(game);
    }

    @Transactional(readOnly = true)
    public List<PlayerInfo> getActivePlayersPlayerInfosByGame(Game game) {
        return repo.findPlayerInfosByGame(game).stream().filter(p -> p.getSpectator() == false).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PlayerInfo getPlayerInfoByGameAndPlayer(Game game, Player player) {
        return repo.findPlayerInfoByGameAndPlayer(game, player);
    }

    @Transactional(readOnly = true)
    public List<Game> getGamesByPlayer(Player player) {
        return repo.findGamesByPlayer(player);
    }

    @Transactional(readOnly = true)
    public List<Player> getPlayersByGame(Game game) {
        return repo.findPlayersByGame(game);
    }

    @Transactional(readOnly = true)
    public List<Player> getAllUsersByGame(Game game) {
        return repo.findAllUsersByGame(game);
    }

    @Transactional(readOnly = true)
    public Boolean isSpectator(Player player, Game game) {
        List<Player> players = repo.findPlayersByGame(game);
        return !players.contains(player);
    }

    @Transactional
    public void saveCreatorInfo(PlayerInfo creatorInfo, Game game, Player player) {
        creatorInfo.setCreator(true);
        creatorInfo.setSpectator(false);
        creatorInfo.setPlayer(player);
        creatorInfo.setGame(game);
        repo.save(creatorInfo);
    }

    @Transactional
    public PlayerInfo saveJoinedPlayerInfo(PlayerInfo joinedInfo, Game game, Player player) {
        List<Player> players=repo.findPlayersByGame(game);
        if(!players.contains(player)){
            joinedInfo.setCreator(false);
            joinedInfo.setSpectator(false);
            joinedInfo.setPlayer(player);
            joinedInfo.setGame(game);
        }
        return repo.save(joinedInfo);
    public PlayerInfo savePlayerInfo(PlayerInfo playerInfo, Game game, Player player) {
        playerInfo.setCreator(false);
        playerInfo.setSpectator(false);
        playerInfo.setPlayer(player);
        playerInfo.setGame(game);
        return repo.save(playerInfo);
    }

    @Transactional
    public PlayerInfo saveSpectatorPlayerInfo(PlayerInfo spectatorInfo, Game game, Player player) {
        List<Player> players=repo.findPlayersByGame(game);
        if(!players.contains(player)){
            spectatorInfo.setCreator(false);
            spectatorInfo.setSpectator(true);
            spectatorInfo.setPlayer(player);
            spectatorInfo.setGame(game);
        }
    public PlayerInfo saveSpectatorInfo(PlayerInfo spectatorInfo, Game game, Player player) {
        spectatorInfo.setCreator(false);
        spectatorInfo.setSpectator(true);
        spectatorInfo.setPlayer(player);
        spectatorInfo.setGame(game);
        return repo.save(spectatorInfo);
    }

}
