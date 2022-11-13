package org.springframework.samples.petclinic.invitation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.player.Player;
import org.springframework.samples.petclinic.player.PlayerService;
import org.springframework.samples.petclinic.user.User;
import org.springframework.samples.petclinic.user.UserService;
import org.springframework.samples.petclinic.web.CurrentUserController;
import org.springframework.samples.petclinic.web.CurrentUserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/invitations")
public class InvitationController {
    
    private static final String INVITATIONS_LIST = "invitations/invitationsList";

    private InvitationService invitationService;

    private UserService userService;

    private PlayerService playerService;

    private CurrentUserService currentUserService;

    private CurrentUserController currentUserController;

    @Autowired
    public InvitationController(InvitationService iS) {
        this.invitationService = iS;
    }

/*    @GetMapping
    public ModelAndView showInvitations(){
        ModelAndView result = new ModelAndView(INVITATIONS_LIST);
        result.addObject("invitations", invitationService.getInvitations());
        return result;
    }*/
    

    @GetMapping
    public ModelAndView showInvitations(){
        ModelAndView result = new ModelAndView(INVITATIONS_LIST);

        User user = userService.findUser(currentUserController.getCurrentUser()).get();
/* 
        UserDetails ud = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.findUser(ud.getUsername()).get();
 */     Player recipient = playerService.getPlayerByUsername(user.getUsername());

        result.addObject("invitations", invitationService.getInvitationsByUser(recipient));
        return result;
    }
}
