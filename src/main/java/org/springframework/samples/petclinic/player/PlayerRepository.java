package org.springframework.samples.petclinic.player;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends CrudRepository<Player, Integer> {
    List<Player> findAll(); 
    Optional<Player> findById(Integer id);
}
