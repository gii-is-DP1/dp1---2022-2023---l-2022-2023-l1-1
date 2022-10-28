package org.springframework.samples.petclinic.voteCard;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.samples.petclinic.model.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "voteCards")
public class VoteCard extends BaseEntity{
    @Enumerated(EnumType.STRING)
    @NotNull
    private VoteType voteType;

}
