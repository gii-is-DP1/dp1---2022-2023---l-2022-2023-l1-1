package org.springframework.samples.petclinic.deck;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeckRepository extends CrudRepository<Deck, Integer> {
    List<Deck> findAll();
}
