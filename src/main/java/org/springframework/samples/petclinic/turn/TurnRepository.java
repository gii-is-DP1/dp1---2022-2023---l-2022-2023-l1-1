package org.springframework.samples.petclinic.turn;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.round.Round;
import org.springframework.stereotype.Repository;

@Repository
public interface TurnRepository extends CrudRepository<Turn, Long>{

    @Query("SELECT t FROM Turn t WHERE t.round LIKE :round")
    public Turn findTurnByRound (@Param("round") Round round);


}
