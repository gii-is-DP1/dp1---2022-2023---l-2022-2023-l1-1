package org.springframework.samples.petclinic.stage;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.turn.Turn;
import org.springframework.stereotype.Repository;

@Repository
public interface StageRepository extends CrudRepository<Stage, Long>{

    @Query("SELECT DISTINCT s FROM Stage s WHERE s.turn LIKE :turn")
    public Stage findStageByTurn(@Param("turn") Turn turn);
}