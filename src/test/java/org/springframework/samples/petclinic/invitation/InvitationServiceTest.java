package org.springframework.samples.petclinic.invitation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.player.Player;
import org.springframework.samples.petclinic.player.PlayerService;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class InvitationServiceTest {

    @Autowired
    private InvitationService invitationService;

    @Autowired
    private PlayerService playerService;

    @Test
    public void testGetInvitationsByPlayer() {
        Player p = playerService.getPlayerByUsername("alvgonfri");
        List<Invitation> invitations = invitationService.getInvitationsByPlayer(p);
        assertNotNull(invitations);
        assertFalse(invitations.isEmpty());
        assertEquals(invitations.size(), 2);
    }
    
}
