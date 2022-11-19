package org.springframework.samples.petclinic.invitation;

import java.util.List;

import javax.transaction.TransactionScoped;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.player.Player;
import org.springframework.samples.petclinic.player.PlayerService;
import org.springframework.samples.petclinic.user.User;
import org.springframework.samples.petclinic.user.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/invitations")
public class InvitationController {
    
    private static final String INVITATIONS_LIST = "invitations/invitationsList";
    private static final String SEND_INVITATION = "invitations/sendInvitation";

    @Autowired
    private InvitationService invitationService;

    @Autowired
    private PlayerService playerService;

    @Autowired
    private UserService userService;

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

    @Transactional
    @GetMapping("/send")
    public ModelAndView sendInvitation() {
        Invitation i = new Invitation();
        List<Player> allPlayers = playerService.getAll();
        ModelAndView result = new ModelAndView(SEND_INVITATION);
        result.addObject("players", allPlayers);
        result.addObject("invitation", i);
        return result;
    }

    @Transactional
    @PostMapping("/send")
    public ModelAndView saveInvitation(@Valid Invitation i, @AuthenticationPrincipal UserDetails user, BindingResult br) {
        ModelAndView result = null;
        Player sender = playerService.getPlayerByUsername(user.getUsername());
        if(br.hasErrors()) {
            return new ModelAndView(SEND_INVITATION, br.getModel());
        } else {
            invitationService.saveInvitation(i, sender);
            result = showInvitationsByPlayer(user);
            result.addObject("message", "Invitation sent succesfully!");
        }
        return result;
    }
}
