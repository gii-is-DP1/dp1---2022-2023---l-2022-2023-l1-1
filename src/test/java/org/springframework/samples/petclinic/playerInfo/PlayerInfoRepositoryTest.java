package org.springframework.samples.petclinic.playerInfo;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.samples.petclinic.game.Game;
import org.springframework.samples.petclinic.game.GameRepository;
import org.springframework.samples.petclinic.player.Player;
import org.springframework.samples.petclinic.player.PlayerRepository;

@DataJpaTest
public class PlayerInfoRepositoryTest {

    @Autowired
    private PlayerInfoRepository playerInfoRepository;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @Test
    public void testFindPlayerInfosByGame() {
        Game g = gameRepository.findById(2);
        List<PlayerInfo> ls = playerInfoRepository.findPlayerInfosByGame(g);
        assertNotNull(ls);
        assertFalse(ls.isEmpty());
    }

    @Test
    public void testFindGamesByPlayer() {
        Player p = playerRepository.getPlayerByUsername("alvgonfri");
        List<Game> ls = playerInfoRepository.findGamesByPlayer(p);
        assertNotNull(ls);
        assertFalse(ls.isEmpty());
    }

    
}
