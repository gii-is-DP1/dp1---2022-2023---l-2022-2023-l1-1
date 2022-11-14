package org.springframework.samples.petclinic.invitation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.player.Player;
import org.springframework.samples.petclinic.player.PlayerService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/invitations")
public class InvitationController {
    
    private static final String INVITATIONS_LIST = "invitations/invitationsList";

    @Autowired
    private InvitationService invitationService;

    @Autowired
    private PlayerService playerService;

    @Autowired
    public InvitationController(InvitationService iS) {
        this.invitationService = iS;
    }

    @GetMapping
    public ModelAndView showInvitationsByPlayer(@AuthenticationPrincipal UserDetails user){
        ModelAndView result = new ModelAndView(INVITATIONS_LIST);
        Player recipient = playerService.getPlayerByUsername(user.getUsername());
        result.addObject("invitations", invitationService.getInvitationsByPlayer(recipient));
        return result;
    }
}
