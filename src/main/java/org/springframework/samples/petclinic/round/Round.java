package org.springframework.samples.petclinic.round;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.samples.petclinic.enums.CurrentRound;
import org.springframework.samples.petclinic.game.Game;
import org.springframework.samples.petclinic.model.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "rounds")
public class Round extends BaseEntity{

    @Enumerated(EnumType.STRING)
    private CurrentRound currentRound;

    @OneToOne
    private Game game;

    public Round () {

    }
    
    public Round (Game game) {
        this.currentRound = CurrentRound.FIRST;
        this.game = game;
    }
}
