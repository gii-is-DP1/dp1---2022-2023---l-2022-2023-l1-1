package org.springframework.samples.petclinic.deck;

import java.util.Optional;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.deck.FactionCard.FCType;
import org.springframework.samples.petclinic.enums.Faction;
import org.springframework.stereotype.Repository;

@Repository
public interface FactionCardRepository extends CrudRepository <FactionCard, FCType> {

    
}
