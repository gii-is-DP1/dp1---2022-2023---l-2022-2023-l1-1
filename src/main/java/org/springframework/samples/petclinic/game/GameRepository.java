package org.springframework.samples.petclinic.game;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.playerInfo.PlayerInfo;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends CrudRepository<Game, Long>{
    List<Game> findAll();
    
    @Query("SELECT g FROM Game g")
    List<Game> findAllPageable(Pageable pageable);  // Lo mismo para player

    @Query("SELECT DISTINCT g FROM Game g WHERE g.id LIKE :id")
	public Game findById(@Param("id") Integer id);

    @Query("SELECT DISTINCT g FROM Game g WHERE g.name LIKE :name%")
	public List<Game> findByName(@Param("name") String name);

    @Query("SELECT DISTINCT g FROM Game g NATURAL JOIN PlayerInfo pI WHERE pI.g.id LIKE :id")
    public List<PlayerInfo> findPlayerInfosInGameById(@Param("id") Integer id);

}
