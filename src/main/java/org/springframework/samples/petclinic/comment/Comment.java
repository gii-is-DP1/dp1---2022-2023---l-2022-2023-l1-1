package org.springframework.samples.petclinic.comment;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.springframework.samples.petclinic.model.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "comments")
public class Comment extends BaseEntity{

    @NotBlank
	private String message;

    @NotBlank
    private String sender;
    
}
