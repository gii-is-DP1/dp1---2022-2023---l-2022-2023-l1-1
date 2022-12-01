package org.springframework.samples.petclinic.player;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.samples.petclinic.achievements.Achievement;
import org.springframework.samples.petclinic.user.User;
import org.springframework.samples.petclinic.user.UserService;
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

    @Autowired
    private UserService userService;

    private static final String VIEWS_PLAYER_LIST = "/players/playersList";
    private static final String EDIT_PLAYER = "/players/createOrUpdatePlayerForm";


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

    @GetMapping("/{id}/edit")
    public String getPlayer(@PathVariable("id") Integer id, ModelMap model){
        Player player = playerService.getPlayer(id);
        if(player !=null){
            List<User> allUsers = userService.findAll();
            model.put("users", allUsers);
            model.put("player", player);
            return EDIT_PLAYER;
        } else {
            model.put("message", "The player " + id + " doesn't exist");
            model.put("messageType", "info");
            return listAllPlayers(model);
        }
    }

    @PostMapping("/{id}/edit")
    public String savePlayer(@PathVariable("id")Integer id, @Valid Player player, BindingResult bindingResult, ModelMap model){
        if(bindingResult.hasErrors()){
            return EDIT_PLAYER;
        }
        else {
            Player playerToUpdate = playerService.getPlayer(id);
            if(playerToUpdate != null){
                BeanUtils.copyProperties(player, playerToUpdate, "id");
                model.put("message", "Player " + id + " successfully updated");
                playerService.savePlayer(playerToUpdate);
                return listAllPlayers(model);
            }
            else{
                model.put("message", "Player " + id + " doesn't exist");
                model.put("messageType", "info");
                return listAllPlayers(model);
            }
        }
    }

    @GetMapping("/{id}/delete")
    public String removePlayer(@PathVariable("id") Integer id, ModelMap model){
        String message;

        try{
            playerService.removePlayer(id);
            message = "Player " + id + " successfully deleted";   
        } catch (EmptyResultDataAccessException e){
            message = "Player " + id + " doesn't exist";
        }
        model.put("message", message);
        model.put("messageType", "info");
        return listAllPlayers(model);
    }

    @GetMapping("/create")
    public String addPlayer(ModelMap model){
        List<User> allUsers = userService.findAll();
        model.put("users", allUsers);
        model.put("player", new Player());
        return EDIT_PLAYER;
    }

    @PostMapping("/create")
    public String saveNewPlayer(@Valid Player player, BindingResult bindingResult, ModelMap model){
       if (bindingResult.hasErrors()) {
        return EDIT_PLAYER;
       } else {
        Player newPlayer = new Player();
        BeanUtils.copyProperties(player, newPlayer, "id");
        Player createdPlayer = playerService.savePlayer(newPlayer);
        model.put("message", "Player " + createdPlayer.getId() + " successfully created" );
        return "redirect:/players/";
       }
    }


}
