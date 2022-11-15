package org.springframework.samples.petclinic.playerInfo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.game.Game;
import org.springframework.samples.petclinic.player.Player;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerInfoRepository extends CrudRepository<PlayerInfo,Long>{

    List<PlayerInfo> findAll(); 

    @Query("SELECT pI FROM PlayerInfo pI WHERE pI.game =?1")
	public List<Player> findPlayerInfosByGame(@Param("game") Game game);

}