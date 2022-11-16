package org.springframework.samples.petclinic.player;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.samples.petclinic.model.BaseEntity;
import org.springframework.samples.petclinic.user.User;

import lombok.Getter;
import lombok.Setter;
//PRUEBA GITHUB
@Entity
@Getter
@Setter
@Table(name="players")
public class Player extends BaseEntity {

    @NotNull
    private Boolean online;

    @NotNull
    private Boolean playing;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "username", referencedColumnName = "username")
	private User user;
    
}