package org.springframework.samples.petclinic.player;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends CrudRepository<Player, Integer> {
    List<Player> findAll(); 

    @Query("SELECT p FROM Player p WHERE p.username LIKE :username%")
    public Player getPlayerByUsername(@Param("username") String username);
}
