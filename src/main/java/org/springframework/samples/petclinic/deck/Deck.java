package org.springframework.samples.petclinic.deck;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.springframework.samples.petclinic.enums.RoleCard;
import org.springframework.samples.petclinic.model.BaseEntity;
import org.springframework.samples.petclinic.player.Player;
import org.springframework.samples.petclinic.turn.Turn;

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

    @ManyToMany(targetEntity = FactionCard.class)
    @Column(name = "faction_card")
    private List<FactionCard> factionCards;

    @ManyToMany(targetEntity = VoteCard.class)
    @Column(name = "vote_card")
    private List<VoteCard> voteCards;

    @ManyToOne (optional = false)
    private Player player;

    @ManyToOne (optional = false)
    private Turn turn;

}
