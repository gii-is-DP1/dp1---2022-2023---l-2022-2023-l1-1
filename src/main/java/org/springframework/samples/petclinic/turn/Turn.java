package org.springframework.samples.petclinic.turn;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.hibernate.validator.constraints.Range;
import org.springframework.samples.petclinic.model.BaseEntity;
import org.springframework.samples.petclinic.round.Round;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "turns")
public class Turn extends BaseEntity{
 
    @Range(min = 1, max = 8)
    private Integer currentTurn;

    @OneToOne
    private Round round;

    private Integer votesLoyal;

    private Integer votesTraitor;

    private Integer votesNeutral;

    public Integer getVoteCount() {
        if (this.votesLoyal == null) { this.votesLoyal = 0;}
        if (this.votesTraitor == null) { this.votesTraitor = 0;}
        if (this.votesNeutral == null) {this.votesNeutral = 0;}
        return votesLoyal + votesTraitor + votesNeutral;
    }


    public Turn () {
        
        
    }

    public Turn (Round round) {
        this.currentTurn = 1;
        this.round = round;
        this.votesLoyal = 0;
        this.votesTraitor = 0;
        this.votesNeutral = 0;
    }
}
