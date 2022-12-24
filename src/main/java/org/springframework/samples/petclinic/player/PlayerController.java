package org.springframework.samples.petclinic.player;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/players")
public class PlayerController {
    private PlayerService playerService;

    private static final String VIEWS_PLAYER_LIST = "/users/playersList";
    private static final String PLAYER_REGISTRATION = "/players/playerRegistration";
    private static final String UPDATE_PLAYER_PASSWORD = "/users/updatePlayerPassword";

    @Autowired
    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping
    public String listAllPlayers(ModelMap model){
        List<Player> allPlayers = playerService.getAll();
        model.put("players", allPlayers);
        return VIEWS_PLAYER_LIST;
    }

    @GetMapping("/register")
    public ModelAndView playerRegistration() {
        ModelAndView res = new ModelAndView(PLAYER_REGISTRATION);
        Player player = new Player();       
        res.addObject("player", player);                                
        return res;
    }

	@PostMapping(value = "/register")
	public String savePlayer(@Valid Player player, BindingResult result) {
		if (result.hasErrors()) {
			return PLAYER_REGISTRATION;
		}
		else {
			playerService.savePlayer(player);
			return "redirect:/";
		}
	}

    @GetMapping("/edit")
    public ModelAndView editPlayerForm(@AuthenticationPrincipal UserDetails user) {
		ModelAndView res = new ModelAndView(UPDATE_PLAYER_PASSWORD);
        Player player = playerService.getPlayerByUsername(user.getUsername());        
        res.addObject("player", player);
        return res;
    }

	@PostMapping("/edit")
    public ModelAndView editPlayer(@AuthenticationPrincipal UserDetails user, @Valid Player player, BindingResult br) {
        ModelAndView res = new ModelAndView("welcome");
        if (br.hasErrors()) {
            return new ModelAndView(UPDATE_PLAYER_PASSWORD, br.getModel());
        }
        Player playerToBeUpdated = playerService.getPlayerByUsername(user.getUsername()); 
        BeanUtils.copyProperties(player, playerToBeUpdated,"id", "online", "playing", "progress");
        playerService.saveEditedPlayer(playerToBeUpdated);
        res.addObject("message", "Password changed succesfully!");
        return res;
    }

    @GetMapping("/{id}/delete")
    public String removePlayer(@PathVariable("id") Integer id, ModelMap model){
        String message;
        try{
            playerService.deletePlayer(id);
            message = "Player " + id + " successfully deleted"; 
            return "redirect:/players";
        } catch (EmptyResultDataAccessException e){
            message = "Player " + id + " doesn't exist";
        }
        model.put("message", message);
        model.put("messageType", "info");
        return listAllPlayers(model);
        
    }
}
