package org.springframework.samples.petclinic.turn;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.validator.constraints.Range;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "turns")
public class Turn {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(unique=true, nullable=false, precision=10)
    private long id;

    @Range(min = 5, max = 8)
    private Integer currentTurn;
}
