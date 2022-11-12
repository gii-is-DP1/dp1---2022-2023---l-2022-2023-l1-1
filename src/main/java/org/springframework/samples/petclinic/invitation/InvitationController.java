package org.springframework.samples.petclinic.invitation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.user.User;
import org.springframework.samples.petclinic.user.UserService;
import org.springframework.samples.petclinic.web.CurrentUserController;
import org.springframework.samples.petclinic.web.CurrentUserService;
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

    private CurrentUserService currentUserService;

    @Autowired
    public InvitationController(InvitationService iS) {
        this.invitationService = iS;
    }

    @GetMapping
    public ModelAndView showInvitations(){
        ModelAndView result = new ModelAndView(INVITATIONS_LIST);
        User user = userService.findUser(currentUserService.showCurrentUser()).get();
        result.addObject("invitations", invitationService.getInvitationsByUser(user));
        return result;
    }
}
