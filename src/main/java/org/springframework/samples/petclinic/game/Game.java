package org.springframework.samples.petclinic.game;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.OneToOne;
import javax.persistence.EnumType;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Range;
import org.springframework.samples.petclinic.enums.CurrentRound;
import org.springframework.samples.petclinic.enums.CurrentStage;
import org.springframework.samples.petclinic.enums.Faction;
import org.springframework.samples.petclinic.enums.State;
import org.springframework.samples.petclinic.model.NamedEntity;
import org.springframework.samples.petclinic.suffragiumCard.SuffragiumCard;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "games")
public class Game extends NamedEntity {
    
    @NonNull
    private Boolean publicGame;

    @Enumerated(EnumType.STRING)
    private State state;

    //@Range(min = 5, max = 8) tiene que estar entre 5 y 8 solo si State == In process
    private Integer numPlayers; 

    @NonNull
    private LocalDate date;
    
    private Double duration;

    @NonNull
    private CurrentRound round;

    @NonNull
    private Integer turn;

    @NonNull
    private CurrentStage stage;

    @Enumerated(EnumType.STRING)
    private Faction winners;

    @OneToOne (optional=true)
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
