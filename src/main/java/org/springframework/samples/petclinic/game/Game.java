package org.springframework.samples.petclinic.game;

import java.time.Duration;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.EnumType;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Range;
import org.springframework.samples.petclinic.enums.Faction;
import org.springframework.samples.petclinic.enums.State;
import org.springframework.samples.petclinic.model.NamedEntity;

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

    @Range(min = 5, max = 8)
    private Integer numPlayers;

    private LocalDate date;
    
    private Duration duration;

    private Faction winners;
}
