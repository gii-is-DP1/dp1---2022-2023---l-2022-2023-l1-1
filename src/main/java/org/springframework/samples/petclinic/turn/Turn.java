package org.springframework.samples.petclinic.turn;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.validator.constraints.Range;
import org.springframework.samples.petclinic.model.BaseEntity;
import org.springframework.samples.petclinic.round.Round;

import lombok.Getter;
import lombok.Setter;
//PRUEBA GITHUB1
@Entity
@Getter
@Setter
@Table(name = "turns")
public class Turn extends BaseEntity{
 
    @Range(min = 5, max = 8)
    private Integer currentTurn;

    @ManyToOne
    private Round round;
}
