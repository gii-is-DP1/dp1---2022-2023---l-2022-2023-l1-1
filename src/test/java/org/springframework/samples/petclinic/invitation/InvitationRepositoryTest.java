package org.springframework.samples.petclinic.invitation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.samples.petclinic.player.Player;
import org.springframework.samples.petclinic.player.PlayerRepository;

@DataJpaTest
public class InvitationRepositoryTest {

    @Autowired
    private InvitationRepository invitationRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @Test
    public void testFindInvitationsByPlayerWithInvitations() {
        Player p = playerRepository.getPlayerByUsername("alvgonfri");
        List<Invitation> invitations = invitationRepository.findInvitationsByPlayer(p);
        assertNotNull(invitations);
        assertFalse(invitations.isEmpty());
        assertEquals(invitations.size(), 2);
    }

    @Test
    public void testFindInvitationsByPlayerWithoutInvitations() {
        Player p = playerRepository.getPlayerByUsername("player1");
        List<Invitation> invitations = invitationRepository.findInvitationsByPlayer(p);
        assertNotNull(invitations);
        assertTrue(invitations.isEmpty());
        assertEquals(invitations.size(), 0);
    }

    @Test
    public void testFindInvitationsByPlayerNotExistingPlayer() {
        Player p = playerRepository.getPlayerByUsername("CristianoRonaldo_7");
        List<Invitation> invitations = invitationRepository.findInvitationsByPlayer(p);
        assertNotNull(invitations);
        assertTrue(invitations.isEmpty());
        assertEquals(invitations.size(), 0);
    }
    
}
