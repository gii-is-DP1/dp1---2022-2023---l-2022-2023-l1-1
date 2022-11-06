package org.springframework.samples.petclinic.deck;

import javax.persistence.Entity;

import org.springframework.samples.petclinic.model.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class FactionCard extends BaseEntity{
    
    private CardType cardType;
    
    private enum CardType {
        LOYAL, TRAITOR, MERCHANT
    }
}
