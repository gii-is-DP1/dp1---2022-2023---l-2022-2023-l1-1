package org.springframework.samples.petclinic.deck;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.enums.Faction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DeckService {

    DeckRepository rep;

    @Autowired
    public DeckService(DeckRepository rep) {
        this.rep = rep;
    }

    public Deck getPlayerDeck (Integer playerId) {
        return rep.findPlayerDeck(playerId);
    }

    
    public void saveDeck (Deck deck) {
        rep.save(deck);
    }

    public void updateFactionDeck (Deck deck, List<FactionCard> fc) {
        Deck deckToUpdate = rep.findById(deck.getId()).get();
        deckToUpdate.setFactionCards(fc);
        rep.save(deckToUpdate);

    } 

    public List<Deck> getDecks() {
        return rep.findAll();
    }
    
}
