package org.springframework.samples.petclinic.player;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.samples.petclinic.deck.Deck;
import org.springframework.samples.petclinic.game.Game;
import org.springframework.samples.petclinic.model.BaseEntity;
import org.springframework.samples.petclinic.progress.Progress;
import org.springframework.samples.petclinic.user.User;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="players")
public class Player extends BaseEntity {

    @NotNull
    private Boolean online;

    @NotNull
    private Boolean playing;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "username", referencedColumnName = "username")
	private User user;

    @OneToMany (mappedBy = "player")
    private List<Deck> decks;

    @OneToMany (mappedBy = "player")
    private List<Progress> progress;
}