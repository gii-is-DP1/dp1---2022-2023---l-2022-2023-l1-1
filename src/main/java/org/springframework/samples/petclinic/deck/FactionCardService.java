package org.springframework.samples.petclinic.deck;

import javax.persistence.Enumerated;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.deck.FactionCard.FCType;
import org.springframework.stereotype.Service;

@Service
public class FactionCardService {
    
    FactionCardRepository rep;

    @Autowired
    public FactionCardService(FactionCardRepository rep) {
        this.rep = rep;
    }

    public FactionCard getByFaction (FCType type) {
        return rep.findById(type).get();
    }
 
}
