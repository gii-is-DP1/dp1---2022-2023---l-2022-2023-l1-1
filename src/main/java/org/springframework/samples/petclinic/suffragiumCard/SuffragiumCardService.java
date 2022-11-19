package org.springframework.samples.petclinic.suffragiumCard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SuffragiumCardService {

    private SuffragiumCardRepository suffragiumCardRepository;

    @Autowired
    public SuffragiumCardService(SuffragiumCardRepository repo) {
        this.suffragiumCardRepository = repo;
    }

    @Transactional
    public SuffragiumCard saveSuffragiumCard(SuffragiumCard card) throws DataAccessException{
        card.setLoyalsVotes(0);
        card.setTraitorsVotes(0);
        card.setVoteLimit(15);
        return suffragiumCardRepository.save(card);
    }
    
}
