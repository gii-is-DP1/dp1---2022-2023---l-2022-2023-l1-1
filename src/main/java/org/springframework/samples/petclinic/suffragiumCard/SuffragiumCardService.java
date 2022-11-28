package org.springframework.samples.petclinic.suffragiumCard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.game.Game;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SuffragiumCardService {

    private SuffragiumCardRepository suffragiumCardRepository;

    @Autowired
    public SuffragiumCardService(SuffragiumCardRepository repo) {
        this.suffragiumCardRepository = repo;
    }

    @Transactional(readOnly = true)
    public SuffragiumCard getSuffragiumCardByGame(Integer gameId) {
        return suffragiumCardRepository.findSuffragiumCardByGame(gameId);
    }

    public void updateVotes (SuffragiumCard card, Integer loyalVotes, Integer traitorVotes) {
        SuffragiumCard cardToUpdate = suffragiumCardRepository.findById(card.getId()).get();
        cardToUpdate.setLoyalsVotes(cardToUpdate.getLoyalsVotes() + loyalVotes);
        cardToUpdate.setTraitorsVotes(cardToUpdate.getTraitorsVotes() + traitorVotes);
        suffragiumCardRepository.save(cardToUpdate);
    }

    @Transactional
    public SuffragiumCard createSuffragiumCardIfNeeded(Game game) throws DataAccessException{
        SuffragiumCard card = suffragiumCardRepository.findSuffragiumCardByGame(game.getId());
        if(card==null) {
            SuffragiumCard suffragiumCard = new SuffragiumCard();
            suffragiumCard.setLoyalsVotes(0);
            suffragiumCard.setTraitorsVotes(0);
            suffragiumCard.setVoteLimit(15);
            return suffragiumCardRepository.save(suffragiumCard);
        }
        return card;
    }
    
}
