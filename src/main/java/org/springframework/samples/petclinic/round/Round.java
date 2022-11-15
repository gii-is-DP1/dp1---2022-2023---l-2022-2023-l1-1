package org.springframework.samples.petclinic.round;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.samples.petclinic.enums.CurrentRound;
import org.springframework.samples.petclinic.model.BaseEntity;
import org.springframework.samples.petclinic.turn.Turn;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "rounds")
public class Round extends BaseEntity{

    @Enumerated(EnumType.STRING)
    private CurrentRound currentRound;
    
    @OneToMany
    private List<Turn> turn;
}