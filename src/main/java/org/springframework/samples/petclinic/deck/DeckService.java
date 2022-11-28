package org.springframework.samples.petclinic.deck;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.deck.FactionCard.FCType;
import org.springframework.samples.petclinic.deck.VoteCard.VCType;
import org.springframework.samples.petclinic.enums.RoleCard;
import org.springframework.samples.petclinic.game.Game;
import org.springframework.samples.petclinic.player.Player;
import org.springframework.samples.petclinic.playerInfo.PlayerInfoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DeckService {

    DeckRepository rep;

    @Autowired
    private PlayerInfoRepository playerInfoRepository;

    @Autowired
    private FactionCardRepository factionCardRepository;

    @Autowired
    private VoteCardRepository voteCardRepository;

    @Autowired
    public DeckService(DeckRepository rep) {
        this.rep = rep;
    }

    @Transactional(readOnly = true)
    public Deck getPlayerGameDeck (Integer playerId, Integer gameId) {
        return rep.findPlayerDeck(playerId).stream().filter(x -> x.getGame().getId() == gameId).findFirst().get();
    }

    @Transactional
    public void saveDeck (Deck deck) {
        rep.save(deck);
    }

    @Transactional
    public void updateFactionDeck (Deck deck, List<FactionCard> fc) {
        Deck deckToUpdate = rep.findById(deck.getId()).get();
        deckToUpdate.setFactionCards(fc);
        rep.save(deckToUpdate);
    } 

    @Transactional
    public List<Deck> getDecks() {
        return rep.findAll();
    }

    @Transactional(readOnly = true)
    public List<FactionCard> getFactionCards(Integer numPlayers) {
        List<FactionCard> factions = new ArrayList<>();
        for(int i=0; i<numPlayers-1; i++) {
            factions.add(factionCardRepository.findById(FCType.LOYAL).get());
            factions.add(factionCardRepository.findById(FCType.TRAITOR).get());
        }
        factions.add(factionCardRepository.findById(FCType.MERCHANT).get());
        factions.add(factionCardRepository.findById(FCType.MERCHANT).get());
        return factions;
    }

    @Transactional(readOnly = true)
    public List<FactionCard> getPlayerFactionCards(List<FactionCard> factions) {
        List<FactionCard> res = new ArrayList<>();
        int faction1 = (int) (Math.random() * (factions.size()-1));
        res.add(factions.get(faction1));
        factions.remove(faction1);
        int faction2 = (int) (Math.random() * (factions.size()-1));
        res.add(factions.get(faction2));
        factions.remove(faction2);
        return res;
    }

    @Transactional(readOnly = true)
    public List<VoteCard> getFirstRoundVoteCards() {
        List<VoteCard> res = new ArrayList<>();
        res.add(voteCardRepository.findById(VCType.GREEN).get());
        res.add(voteCardRepository.findById(VCType.RED).get());
        return res;
    }

    @Transactional
    public void assingDecks(Game game) {        
        List<Player> players = playerInfoRepository.findPlayersByGame(game);
        System.out.println(players);
        List<FactionCard> factions = getFactionCards(players.size());
        List<VoteCard> votes = getFirstRoundVoteCards();
        Integer consul = (int) (Math.random() * (players.size()-1));
        Integer pretor = (consul+1) % (players.size());
        Integer edil1 = (pretor+1) % (players.size());
        Integer edil2 = (edil1+1) % (players.size());
        for(int i=0; i<players.size(); i++) {
            Deck deck = new Deck();
            deck.setGame(game);
            deck.setPlayer(players.get(i));
            if(i == consul) {
                deck.setRoleCard(RoleCard.CONSUL);
                List<FactionCard> playerFactions = getPlayerFactionCards(factions);
                deck.setFactionCards(playerFactions);
                deck.setVoteCards(new ArrayList<>());
            } 
            else if(i == pretor) {
                deck.setRoleCard(RoleCard.PRETOR);
                List<FactionCard> playerFactions = getPlayerFactionCards(factions);
                deck.setFactionCards(playerFactions);
                deck.setVoteCards(new ArrayList<>());
            }
               
            else if(i == edil1 || i == edil2) {
                deck.setRoleCard(RoleCard.EDIL);
                List<FactionCard> playerFactions = getPlayerFactionCards(factions);
                deck.setFactionCards(playerFactions);
                deck.setVoteCards(votes);
            } 
            else {
                deck.setRoleCard(RoleCard.NO_ROL);
                List<FactionCard> playerFactions = getPlayerFactionCards(factions);
                deck.setFactionCards(playerFactions);
                deck.setVoteCards(new ArrayList<>());
            } 
            rep.save(deck);
        }
    }
    
}
