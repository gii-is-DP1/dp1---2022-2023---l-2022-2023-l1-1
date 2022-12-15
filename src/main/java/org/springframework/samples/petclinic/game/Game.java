package org.springframework.samples.petclinic.game;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.OneToOne;
import javax.persistence.EnumType;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;
import org.springframework.samples.petclinic.enums.CurrentRound;
import org.springframework.samples.petclinic.enums.CurrentStage;
import org.springframework.samples.petclinic.enums.Faction;
import org.springframework.samples.petclinic.enums.State;
import org.springframework.samples.petclinic.model.NamedEntity;
import org.springframework.samples.petclinic.suffragiumCard.SuffragiumCard;
import org.springframework.samples.petclinic.turn.Turn;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "games")
public class Game extends NamedEntity {
    
    private Boolean publicGame;

    @Enumerated(EnumType.STRING)
    private State state;

    //@Range(min = 5, max = 8) tiene que estar entre 5 y 8 solo si State == In process
    private Integer numPlayers; 

    private LocalDate date;
    
    private Double duration;

    @Enumerated(EnumType.STRING)
    private CurrentRound round;

    @OneToOne(optional = true)
    private Turn turn;

    @Enumerated(EnumType.STRING)
    private CurrentStage stage;

    @Enumerated(EnumType.STRING)
    private Faction winners;

    @OneToOne(optional = true)
    private SuffragiumCard suffragiumCard;


    public Integer getSuffragiumLimit() {
        Integer players = this.getNumPlayers();
        Integer res = null;
        if (players == 5) {
         res = 13;
        }
        else if (players == 6) {
         res = 15;
        }
        else if (players == 7) {
         res = 17;
        }
        else if (players == 8) {
         res = 20;
        }
        return res;
     }
}
