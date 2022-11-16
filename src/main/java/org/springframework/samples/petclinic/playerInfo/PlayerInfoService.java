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
}
