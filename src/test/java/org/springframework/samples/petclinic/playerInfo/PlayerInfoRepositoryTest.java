package org.springframework.samples.petclinic.playerInfo;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
        Game game = gameRepository.findById(2);
        List<PlayerInfo> playerInfos = playerInfoRepository.findPlayerInfosByGame(game);
        assertNotNull(playerInfos);
        assertFalse(playerInfos.isEmpty());
    }

    @Test
    public void testFindPlayerInfosByGameWithoutPlayerInfos() {
        Game game = gameRepository.findById(4);
        List<PlayerInfo> playerInfos = playerInfoRepository.findPlayerInfosByGame(game);
        assertNotNull(playerInfos);
        assertTrue(playerInfos.isEmpty());
    }

    @Test
    public void testFindGamesByPlayer() {
        Player player = playerRepository.findPlayerByUsername("alvgonfri");
        List<Game> games = playerInfoRepository.findGamesByPlayer(player);
        assertNotNull(games);
        assertFalse(games.isEmpty());
    }

    @Test
    public void testFindGamesByPlayerWithoutGames() {
        Player player = playerRepository.findPlayerByUsername("player5");
        List<Game> games = playerInfoRepository.findGamesByPlayer(player);
        assertNotNull(games);
        assertTrue(games.isEmpty());
    }

    @Test
    public void testFindPlayersByGame() {
        Game game = gameRepository.findById(2);
        List<Player> players = playerInfoRepository.findPlayersByGame(game);
        assertNotNull(players);
        assertFalse(players.isEmpty());
    }

    @Test
    public void testFindPlayersByGameWithoutPlayers() {
        Game game = gameRepository.findById(4);
        List<Player> players = playerInfoRepository.findPlayersByGame(game);
        assertNotNull(players);
        assertTrue(players.isEmpty());
    }

    
}
