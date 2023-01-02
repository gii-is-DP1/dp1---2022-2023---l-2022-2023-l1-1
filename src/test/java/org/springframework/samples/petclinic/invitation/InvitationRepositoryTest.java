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
/* 
    @Test
    public void testFindInvitationsReceivedWithInvitations() {
        Player p = playerRepository.findPlayerByUsername("alvgonfri");
        List<Invitation> invitations = invitationRepository.findInvitationsReceived(p);
        assertNotNull(invitations);
        assertFalse(invitations.isEmpty());
    }

    @Test
    public void testFindInvitationsReceivedWithoutInvitations() {
        Player p = playerRepository.findPlayerByUsername("player1");
        List<Invitation> invitations = invitationRepository.findInvitationsReceived(p);
        assertNotNull(invitations);
        assertTrue(invitations.isEmpty());
    }

    @Test
    public void testFindInvitationsReceivedNotExistingPlayer() {
        Player p = playerRepository.findPlayerByUsername("CristianoRonaldo_7");
        List<Invitation> invitations = invitationRepository.findInvitationsReceived(p);
        assertNotNull(invitations);
        assertTrue(invitations.isEmpty());
    }

    @Test
    public void testFindInvitationsSentWithInvitations() {
        Player p = playerRepository.findPlayerByUsername("alvgonfri");
        List<Invitation> invitations = invitationRepository.findInvitationsSent(p);
        assertNotNull(invitations);
        assertFalse(invitations.isEmpty());
    }

    @Test
    public void testFindInvitationsSentWithoutInvitations() {
        Player p = playerRepository.findPlayerByUsername("player5");
        List<Invitation> invitations = invitationRepository.findInvitationsSent(p);
        assertNotNull(invitations);
        assertTrue(invitations.isEmpty());
    }

    @Test
    public void testFindInvitationsSentNotExistingPlayer() {
        Player p = playerRepository.findPlayerByUsername("CristianoRonaldo_7");
        List<Invitation> invitations = invitationRepository.findInvitationsSent(p);
        assertNotNull(invitations);
        assertTrue(invitations.isEmpty());
    }*/
    
}
