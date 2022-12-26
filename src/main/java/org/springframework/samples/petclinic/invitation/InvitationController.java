package org.springframework.samples.petclinic.invitation;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.samples.petclinic.invitation.exceptions.DuplicatedInvitationException;
import org.springframework.samples.petclinic.invitation.exceptions.NullRecipientException;
import org.springframework.samples.petclinic.player.Player;
import org.springframework.samples.petclinic.player.PlayerService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping("")
public class InvitationController {
    
    private static final String INVITATIONS_LIST = "invitations/invitationsList";
    private static final String SEND_INVITATION = "invitations/sendInvitation";
    private static final String FRIENDS_LIST = "invitations/friendsList";

    @Autowired
    private InvitationService invitationService;

    @Autowired
    private PlayerService playerService;

    @Autowired
    public InvitationController(InvitationService iS) {
        this.invitationService = iS;
    }

    @Transactional
    @GetMapping("/invitations")
    public ModelAndView showInvitationsByPlayer(@AuthenticationPrincipal UserDetails user){
        ModelAndView result = new ModelAndView(INVITATIONS_LIST);
        Player recipient = playerService.getPlayerByUsername(user.getUsername());
        result.addObject("invitations", invitationService.getInvitationsReceived(recipient));
        return result;
    }

    @Transactional
    @GetMapping("/friends")
    public ModelAndView showFriends(@AuthenticationPrincipal UserDetails user) {
        ModelAndView result = new ModelAndView(FRIENDS_LIST);
        Player recipient = playerService.getPlayerByUsername(user.getUsername());
        result.addObject("friendsInvitations", invitationService.getFriendsInvitations(recipient));
        return result;
    }

    @Transactional
    @GetMapping("/invitations/send")
    public ModelAndView sendInvitation(@AuthenticationPrincipal UserDetails user) {
        Invitation invitation = new Invitation();
        Player sender = playerService.getPlayerByUsername(user.getUsername());
        List<Player> players = playerService.getAll();
        List<Player> senderFriends = invitationService.getFriends(sender);
        players.remove(sender);
        players.removeAll(senderFriends);
        ModelAndView result = new ModelAndView(SEND_INVITATION);
        result.addObject("players", players);
        result.addObject("invitation", invitation);
        return result;
    }

    @PostMapping("/invitations/send")
    public ModelAndView saveInvitation(@Valid Invitation invitation, BindingResult br, @AuthenticationPrincipal UserDetails user) throws DuplicatedInvitationException {
        ModelAndView result = null;
        Player sender = playerService.getPlayerByUsername(user.getUsername());
        List<Player> players = playerService.getAll();
        List<Player> senderFriends = invitationService.getFriends(sender);
        players.remove(sender);
        players.removeAll(senderFriends);
        if(br.hasErrors()) {
            Map<String, Object> map = br.getModel();
            map.put("players", players);
            map.put("invitation", invitation);
            return new ModelAndView(SEND_INVITATION, map);
        } else {
            try {
                invitationService.saveInvitation(invitation, sender);
                result = showInvitationsByPlayer(user);
                result.addObject("message", "Invitation sent succesfully!");
            } catch (NullRecipientException e) {
                result = new ModelAndView(SEND_INVITATION);
                result.addObject("players", players);
                result.addObject("invitation", invitation);
                result.addObject("message", "Please, select the player who you want to invite");
                return result;
            } catch (DuplicatedInvitationException e) {
                result = new ModelAndView(SEND_INVITATION);
                result.addObject("players", players);
                result.addObject("invitation", invitation);
                result.addObject("message", "An invitation between you and that player already exists!");
                return result;
            }
        }
        return result;
    }

    @Transactional
    @GetMapping("/invitations/{id}/accept")
    public ModelAndView acceptInvitation(@PathVariable Integer id, @AuthenticationPrincipal UserDetails user, ModelMap model) {
        invitationService.acceptInvitationById(id);
        model.put("message", "Invitation accepted succesfully!");
        return showInvitationsByPlayer(user);
    }

    @Transactional
    @GetMapping("/invitations/{id}/reject")
    public ModelAndView rejectInvitation(@PathVariable Integer id, @AuthenticationPrincipal UserDetails user, ModelMap model) {
        try{
            invitationService.rejectInvitationById(id);  
            model.put("message", "Invitation rejected succesfully!");     
        } catch(EmptyResultDataAccessException e) {
            model.put("message", "Invitation " + id + " does not exist");
        }
        return showInvitationsByPlayer(user);
    }

    @Transactional
    @GetMapping("/invitations/{id}/cancelFriendship")
    public ModelAndView cancelFriendship(@PathVariable Integer id, @AuthenticationPrincipal UserDetails user, ModelMap model) {
        try{
            invitationService.rejectInvitationById(id);  
            model.put("message", "Friendship cancelled succesfully!");     
        } catch(EmptyResultDataAccessException e) {
            model.put("message", "That's not your friend");
        }
        return showInvitationsByPlayer(user);
    }

}
