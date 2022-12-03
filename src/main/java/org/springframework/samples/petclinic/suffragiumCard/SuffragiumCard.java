package org.springframework.samples.petclinic.suffragiumCard;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.samples.petclinic.game.Game;
import org.springframework.samples.petclinic.model.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "suffragium_cards")
public class SuffragiumCard extends BaseEntity {
    
    private Integer loyalsVotes;
    private Integer traitorsVotes;
    private Integer voteLimit;
}


/* Integer res = null;
       switch(game.getNumPlayers()) {
        case 5: return res = 13;
        case 6:  return res = 15;
        case 7: return res = 17;
        case 8: return res = 20;
       }
       return res; */