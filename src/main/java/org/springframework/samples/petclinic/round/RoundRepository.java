package org.springframework.samples.petclinic.round;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.game.Game;
import org.springframework.stereotype.Repository;

@Repository
public interface RoundRepository extends CrudRepository<Round,Long>{

    @Query("SELECT DISTINCT r FROM Round r WHERE r.game LIKE :game")
    public Round findRoundByGame(@Param("game") Game game);
}