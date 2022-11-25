package org.springframework.samples.petclinic.deck;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DeckRepository extends CrudRepository<Deck, Integer> {
    List<Deck> findAll();

    @Query("SELECT d FROM Deck d WHERE d.player.id LIKE :id")
	public  List<Deck> findPlayerDeck(@Param("id") Integer playerId);
}
