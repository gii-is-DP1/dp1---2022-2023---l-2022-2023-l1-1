package org.springframework.samples.petclinic.player;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.samples.petclinic.user.User;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="player")
public class Player extends User{

    private boolean spectator;

    
}