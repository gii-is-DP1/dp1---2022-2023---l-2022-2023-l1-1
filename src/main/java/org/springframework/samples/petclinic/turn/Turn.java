package org.springframework.samples.petclinic.turn;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.validator.constraints.Range;
import org.springframework.samples.petclinic.model.BaseEntity;
import org.springframework.samples.petclinic.stage.Stage;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "turns")
public class Turn extends BaseEntity{
 
    @Range(min = 5, max = 8)
    private Integer currentTurn;

    @OneToMany
    private List<Stage> stage;
}
