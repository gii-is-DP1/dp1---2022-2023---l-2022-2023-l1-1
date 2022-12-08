package org.springframework.samples.petclinic.playerInfo;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.samples.petclinic.game.Game;
import org.springframework.samples.petclinic.model.BaseEntity;
import org.springframework.samples.petclinic.player.Player;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "player_infos")
public class PlayerInfo extends BaseEntity{

    private Boolean creator;
    
    private Boolean spectator;
    
    @ManyToOne (optional = false)
    private Player player;

    @ManyToOne (optional = false)
    private Game game;
}
