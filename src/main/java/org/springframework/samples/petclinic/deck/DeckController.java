package org.springframework.samples.petclinic.deck;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/decks")
public class DeckController {

    @Autowired
    private DeckService deckService;
    
    @Autowired
    public DeckController(DeckService deckService){
        this.deckService = deckService;
    }

   

    
}
