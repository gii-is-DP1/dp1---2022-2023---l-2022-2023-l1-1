package org.springframework.samples.petclinic.invitation;

import org.springframework.stereotype.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.player.Player;
import org.springframework.samples.petclinic.user.User;

@Repository
public interface InvitationRepository extends CrudRepository<Invitation, Integer> {

    public List<Invitation> findAll();
    
    @Query("SELECT i FROM Invitation i WHERE i.recipient LIKE :recipient%")
    public List<Invitation> findInvitationsByUser(@Param("recipient") Player recipient);
}
