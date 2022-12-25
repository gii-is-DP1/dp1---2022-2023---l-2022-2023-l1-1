package org.springframework.samples.petclinic.progress;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

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
    
    @Column (name = "completed_percentage")
    @Min(0)
    @Max(100)
    private double completedPercentage;

    @ManyToOne
    @JoinColumn(name = "achievement_id")
    private Achievement achievement;

    @ManyToOne
    @JoinColumn (name = "player_id")    
    private Player player;

    public boolean getCompleted() {
        if (completedPercentage == 100.00) {
            return true;
        }
        else {
            return false;
        }
    }

    public Progress (Double completedPercentage, Achievement achievement, Player player) {
        this.completedPercentage = completedPercentage;
        this.achievement = achievement;
        this.player = player;
    }

    public Progress() {

    }
}
