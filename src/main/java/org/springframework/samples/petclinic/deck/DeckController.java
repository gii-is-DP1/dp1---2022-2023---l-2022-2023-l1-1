package org.springframework.samples.petclinic.deck;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EnumType;

import org.h2.constraint.Constraint.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.deck.FactionCard.FCType;
import org.springframework.samples.petclinic.enums.Faction;
import org.springframework.samples.petclinic.player.Player;
import org.springframework.samples.petclinic.player.PlayerService;
import org.springframework.samples.petclinic.playerInfo.PlayerInfo;
import org.springframework.samples.petclinic.playerInfo.PlayerInfoService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/decks")
public class DeckController {


    private DeckService deckService;
    private PlayerService playerService;
    private FactionCardService factionCardService;
    

    @Autowired
    public DeckController(DeckService deckService, PlayerService playerService, FactionCardService factionCardService){
        this.deckService = deckService;
        this.playerService = playerService;
        this.factionCardService = factionCardService;
    }

    @Transactional
    @GetMapping("/edit/{factionType}")
    public String selectFaction (@PathVariable("factionType") String factionType, @AuthenticationPrincipal UserDetails user){
        
        Player player = playerService.getPlayerByUsername(user.getUsername()); //cojo al player que esta loggeado (es el que esta eligiendo su faccion)
        Deck deck = deckService.getPlayerDeck(player.getId()); //cojo el mazo de este 
        List<FactionCard> chosenFaction = new ArrayList<>();
        chosenFaction.add(factionCardService.getByFaction(FCType.valueOf(factionType)));
        deckService.updateFactionDeck(deck, chosenFaction);  
        System.out.println(deckService.getPlayerDeck(player.getId()).getFactionCards().size());     
        return "redirect:/games/2";
    }

    
}
