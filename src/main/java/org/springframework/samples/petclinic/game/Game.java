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
import org.springframework.samples.petclinic.enums.Faction;
import org.springframework.samples.petclinic.enums.State;
import org.springframework.samples.petclinic.model.NamedEntity;
import org.springframework.samples.petclinic.suffragiumCard.SuffragiumCard;

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
    private Faction winners;

    @OneToOne (optional=true)
    private SuffragiumCard suffragiumCard;
}
