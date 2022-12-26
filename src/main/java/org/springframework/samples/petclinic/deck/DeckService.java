package org.springframework.samples.petclinic.deck;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.deck.FactionCard.FCType;
import org.springframework.samples.petclinic.deck.VoteCard.VCType;
import org.springframework.samples.petclinic.enums.CurrentRound;
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
    public Deck getDeckByPlayerAndGame(Player player, Game game) {
        return rep.findPlayerDecks(player.getId()).stream().filter(x -> x.getGame().getId() == game.getId()).findFirst().get();
    }

    @Transactional
    public void saveDeck (Deck deck) {
        rep.save(deck);
    }

    @Transactional
    public void updateFactionDeck (Deck deck, FCType factionCard) {
        List<FactionCard> chosenFaction = new ArrayList<>();
        FactionCard cardChosen = factionCardRepository.findById(factionCard).get();
        chosenFaction.add(cardChosen);
        Deck deckToUpdate = rep.findById(deck.getId()).get();
        deckToUpdate.setFactionCards(chosenFaction);
        rep.save(deckToUpdate);
    }
    
    @Transactional
    public void updateVotesDeck (Deck deck, VCType voteCard) {
        List<VoteCard> chosenVote = new ArrayList<>();
        VoteCard cardChosen = voteCardRepository.findById(voteCard).get();
        chosenVote.add(cardChosen);
        Deck deckToUpdate = rep.findById(deck.getId()).get();
        deckToUpdate.setVoteCards(chosenVote);
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

    public void clearVoteCards (Deck deck) {
        deck.setVoteCards(new ArrayList<>());
        rep.save(deck);

    }

    @Transactional(readOnly = true)
    public List<VoteCard> getFirstRoundVoteCards() {
        List<VoteCard> res = new ArrayList<>();
        res.add(voteCardRepository.findById(VCType.GREEN).get());
        res.add(voteCardRepository.findById(VCType.RED).get());
        return res;
    }

    private static final Integer ANY_PLAYER = 0;

    @Transactional
    public void assingDecksIfNeeded(Game game) {        
        List<Player> players = playerInfoRepository.findPlayersByGame(game);
        if(rep.findDecksByPlayerAndGame(players.get(ANY_PLAYER), game).isEmpty()) {
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

    
    public void deckRotation (Game game) {
        List<Player> players = playerInfoRepository.findPlayersByGame(game);
        Player consulPlayer = getDecks().stream()
            .filter(x -> x.getGame() == game).filter(y -> y.getRoleCard() == RoleCard.CONSUL).findFirst().get().getPlayer();
        Integer consulId = players.indexOf(consulPlayer);

        for(int i=0; i<5; i++) {
            Deck deckToUpdate =  getDeckByPlayerAndGame(players.get((consulId + i) % (players.size())), game);
            if (i == 0) { //consul pasa a noRol
                deckToUpdate.setRoleCard(RoleCard.NO_ROL);
            }
            else if (i == 1) { //pretor pasa a consul
                deckToUpdate.setRoleCard(RoleCard.CONSUL);
                
            }
            else if (i == 2) { //edil1 pasa a pretor
                clearVoteCards(deckToUpdate);
                deckToUpdate.setRoleCard(RoleCard.PRETOR);

            }
            else { //los dos nuevos ediles
                List<VoteCard> newVotes = new ArrayList<>();
                if (deckToUpdate.getVoteCards().size() != 0) { //si tiene algun voto se lo quitamos
                    clearVoteCards(deckToUpdate);
                }
                if (deckToUpdate.getRoleCard() != RoleCard.EDIL) { //si no es ya edil le damos edil
                    deckToUpdate.setRoleCard(RoleCard.EDIL);
                }
                if (game.getRound() == CurrentRound.SECOND) {
                    newVotes.add(voteCardRepository.findById(VCType.YELLOW).get());
                }
                newVotes.add(voteCardRepository.findById(VCType.GREEN).get());
                newVotes.add(voteCardRepository.findById(VCType.RED).get());
                deckToUpdate.setVoteCards(newVotes);
            }
            rep.save(deckToUpdate);
        }
    }
    
}
