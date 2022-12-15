package org.springframework.samples.petclinic.invitation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.player.Player;
import org.springframework.samples.petclinic.player.PlayerService;
import org.springframework.stereotype.Service;

@ExtendWith(MockitoExtension.class)
public class InvitationServiceTest {

    @Mock
    InvitationRepository invitationRepository;

    private Invitation createInvitation(Player recipient, String message) {
        Invitation invitation = new Invitation();
        invitation.setSender(recipient);
        invitation.setMessage(message);
        return invitation;
    }

    @Test
    public void testSaveInvitation() {
        Invitation invitation = createInvitation(new Player(), "Hi, this is a test invitation");
        InvitationService service = new InvitationService(invitationRepository);
        try {
            service.saveInvitation(invitation, new Player());
        } catch (Exception e) {
            fail("no exception should be thrown");
        }
    }

/* 
    @Test
    public void testGetFriends() {
        Player p = createPlayer()
        List<Player> friends = invitationService.getFriends(p);
        assertNotNull(friends);
        assertFalse(friends.isEmpty());
    }*/

    /* 
    @Test
    public void testAcceptInvitationById() {

    }*/
    
}
