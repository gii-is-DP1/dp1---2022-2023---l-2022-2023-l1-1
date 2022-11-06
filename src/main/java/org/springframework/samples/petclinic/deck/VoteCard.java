package org.springframework.samples.petclinic.deck;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.samples.petclinic.model.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="vote_cards")
public class VoteCard {
    
    @Id
    @Enumerated(EnumType.STRING)
    private Type type;
    
    private enum Type {
        GREEN, RED, YELLOW;
    }
}
