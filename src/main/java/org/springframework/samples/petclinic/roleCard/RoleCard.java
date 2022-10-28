package org.springframework.samples.petclinic.roleCard;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.samples.petclinic.model.BaseEntity;

import lombok.Getter;

@Getter
@Entity
@Table(name = "rolecards")
public class RoleCard extends BaseEntity{
    @Enumerated(EnumType.STRING)
    @NotNull
    private Role role;
}
