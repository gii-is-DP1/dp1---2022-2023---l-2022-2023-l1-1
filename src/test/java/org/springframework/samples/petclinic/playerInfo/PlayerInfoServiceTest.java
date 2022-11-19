package org.springframework.samples.petclinic.playerInfo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.samples.petclinic.game.Game;
import org.springframework.samples.petclinic.player.Player;

@ExtendWith(MockitoExtension.class)
public class PlayerInfoServiceTest {

    @Mock
    PlayerInfoRepository repo;

    private PlayerInfo createCreatorInfo(Boolean creator, Boolean spectator, Game game, Player player) {
        PlayerInfo creatorInfo = new PlayerInfo();
        creatorInfo.setCreator(creator);
        creatorInfo.setSpectator(spectator);
        creatorInfo.setGame(game);
        return creatorInfo;
    }

    private Game createGame(String name, Boolean publicGame) {
        Game game = new Game();
        game.setName(name);
        game.setPublicGame(publicGame);
        return game;
    }
/* 
    @Test
    public void testSaveCreatorInfoSuccessful() {
        Game game = createGame("Test game", true);
        Player player = createPlayer();
        PlayerInfo creatorInfo = createCreatorInfo(true, false, game, player);
    }
*/    
}
