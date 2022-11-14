package org.springframework.samples.petclinic.invitation;

import org.springframework.stereotype.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.player.Player;

@Repository
public interface InvitationRepository extends CrudRepository<Invitation, Integer> {
    
    @Query("SELECT i FROM Invitation i WHERE i.recipient=:recipient")
    public List<Invitation> findInvitationsByPlayer(@Param("recipient") Player recipient);
}
