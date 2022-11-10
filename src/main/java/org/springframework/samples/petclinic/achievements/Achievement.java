package org.springframework.samples.petclinic.achievements;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import org.springframework.samples.petclinic.model.NamedEntity;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="achievements")
public class Achievement extends NamedEntity {
    
    @NotEmpty
    private String description;
    private boolean completed;
    @Min(0)
    private double threshold;
    @Column(name = "completed_percentage")
    private double completedPercentage;
    public String getActualDescription() {
        return description.replace("<THRESHOLD>", String.valueOf(threshold));
    }
}