package org.springframework.samples.petclinic.suffragiumCard;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class SuffragiumCardServiceTest {

    @Mock
    SuffragiumCardRepository repo;

    private SuffragiumCard createSuffragiumCard(Integer loyalsVotes, Integer traitorsVotes, Integer voteLimit) {
        SuffragiumCard card = new SuffragiumCard();
        card.setLoyalsVotes(loyalsVotes);
        card.setTraitorsVotes(traitorsVotes);
        card.setVoteLimit(voteLimit);
        return card;
    }

    @Test
    @Disabled
    public void testSaveSuffragiumCardSuccessful() {
        SuffragiumCard card = createSuffragiumCard(0, 0, 15);
        SuffragiumCardService service = new SuffragiumCardService(repo);
        try {
            //service.saveSuffragiumCard(card);
        } catch (Exception e) {
            fail("no exception should be thrown");
        }
    }
    
}
