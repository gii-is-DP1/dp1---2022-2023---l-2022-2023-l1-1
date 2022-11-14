package org.springframework.samples.petclinic.progress;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.samples.petclinic.achievements.Achievement;
import org.springframework.samples.petclinic.model.BaseEntity;
import org.springframework.samples.petclinic.player.Player;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="progress")
public class Progress extends BaseEntity {

    private boolean completed;
    @Column (name = "completed_percentage")
    private double completedPercentage;

    @ManyToOne
    @JoinColumn(name = "achievement_id")
    private Achievement achievement;

    @ManyToOne
    @JoinColumn (name = "player_id")    
    private Player player;
}
