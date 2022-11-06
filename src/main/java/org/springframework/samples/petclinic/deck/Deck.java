package org.springframework.samples.petclinic.deck;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;

import org.springframework.samples.petclinic.enums.RoleCard;
import org.springframework.samples.petclinic.model.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Deck extends BaseEntity {
    
    @NotEmpty
    private RoleCard roleCards;

    @OneToMany(targetEntity = FactionCard.class)
    private List<FactionCard> factionCards;

    @OneToMany(targetEntity = VoteCard.class)
    private List<VoteCard> voteCards;

}
