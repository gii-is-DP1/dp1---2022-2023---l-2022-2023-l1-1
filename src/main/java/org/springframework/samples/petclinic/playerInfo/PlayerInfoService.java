package org.springframework.samples.petclinic.playerInfo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.game.Game;
import org.springframework.samples.petclinic.player.Player;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PlayerInfoService {

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
    public List<PlayerInfo> getPlayerInfosByGame(Game game) {
        return repo.findPlayerInfosByGame(game);
    }

    @Transactional(readOnly = true)
    public List<Game> getGamesByPlayer(Player player) {
        return repo.findGamesByPlayer(player);
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
        joinedInfo.setCreator(false);
        joinedInfo.setSpectator(false);
        joinedInfo.setPlayer(player);
        joinedInfo.setGame(game);
        return repo.save(joinedInfo);
    }

    @Transactional
    public PlayerInfo saveSpectatorPlayerInfo(PlayerInfo spectatorInfo, Game game, Player player) {
        spectatorInfo.setCreator(false);
        spectatorInfo.setSpectator(true);
        spectatorInfo.setPlayer(player);
        spectatorInfo.setGame(game);
        return repo.save(spectatorInfo);
    }
}
