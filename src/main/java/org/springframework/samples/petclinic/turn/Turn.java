package org.springframework.samples.petclinic.turn;

import javax.persistence.Entity;
import javax.persistence.Table;
import org.hibernate.validator.constraints.Range;
import org.springframework.samples.petclinic.model.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "turns")
public class Turn extends BaseEntity{
 
    @Range(min = 5, max = 8)
    private Integer currentTurn;
}
