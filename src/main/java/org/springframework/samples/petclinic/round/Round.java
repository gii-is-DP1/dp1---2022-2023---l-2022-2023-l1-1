package org.springframework.samples.petclinic.round;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.samples.petclinic.enums.CurrentRound;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "rounds")
public class Round{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(unique=true, nullable=false, precision=10)
    private long id;

    @Enumerated(EnumType.STRING)
    private CurrentRound currentRound;
    
}