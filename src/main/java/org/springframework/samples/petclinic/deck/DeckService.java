package org.springframework.samples.petclinic.deck;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeckService {

    DeckRepository rep;

    @Autowired
    public DeckService(DeckRepository rep) {
        this.rep = rep;
    }

    public List<Deck> getDecks() {
        return rep.findAll();
    }
    
}
