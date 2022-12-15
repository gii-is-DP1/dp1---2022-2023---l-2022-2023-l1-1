package org.springframework.samples.petclinic.player;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.samples.petclinic.deck.Deck;
import org.springframework.samples.petclinic.model.BaseEntity;
import org.springframework.samples.petclinic.progress.Progress;
import org.springframework.samples.petclinic.user.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="players")
public class Player extends BaseEntity {

    private Boolean online;

    private Boolean playing;

    @OneToOne
    @JoinColumn(name = "username", referencedColumnName = "username")
	private User user;

    @OneToMany (mappedBy = "player")
    private List<Deck> decks;

    @OneToMany (mappedBy = "player")
    private List<Progress> progress;
}