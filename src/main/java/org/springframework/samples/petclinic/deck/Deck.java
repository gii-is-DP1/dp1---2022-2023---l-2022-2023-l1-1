package org.springframework.samples.petclinic.deck;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.springframework.samples.petclinic.enums.RoleCard;
import org.springframework.samples.petclinic.model.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="decks")
public class Deck extends BaseEntity {
    
    @NotEmpty
    @Enumerated(EnumType.STRING)
    @Column(name = "role_cards")
    private RoleCard roleCards;

    @OneToMany(targetEntity = FactionCard.class)
    @Column(name = "faction_card")
    private List<FactionCard> factionCards;

    @OneToMany(targetEntity = VoteCard.class)
    @Column(name = "vote_card")
    private List<VoteCard> voteCards;

}
