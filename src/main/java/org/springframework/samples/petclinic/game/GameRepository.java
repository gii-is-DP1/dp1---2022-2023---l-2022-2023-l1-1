package org.springframework.samples.petclinic.game;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends CrudRepository<Game, Long>{
    List<Game> findAll(); 

    @Query("SELECT DISTINCT g FROM Game g WHERE g.id LIKE :id")
	public Game findById(@Param("id") Integer id);

    @Query("SELECT DISTINCT g FROM Game g WHERE g.name LIKE :name%")
	public List<Game> findByName(@Param("name") String name);

}
