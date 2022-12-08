/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.user;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.samples.petclinic.player.Player;
import org.springframework.samples.petclinic.player.PlayerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Juergen Hoeller
 * @author Ken Krebs
 * @author Arjen Poutsma
 * @author Michael Isvy
 */
@Controller
@RequestMapping("/users")
public class UserController {

	private static final String VIEWS_USER_LIST = "/users/usersList";
	private static final String CREATE_USER = "/users/createUser";
	private static final String CREATE_UPDATE_PLAYER = "/users/createOrUpdatePlayer";

	@Autowired
    private UserService userService;
	@Autowired
	private AuthoritiesService authoritiesService;
	@Autowired
	private PlayerService playerService;

	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}

	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	@GetMapping
    public String listAllUsers(ModelMap model){
        Iterable<User> allUsers = userService.findAll();
        model.put("users", allUsers);
        return VIEWS_USER_LIST;
    }

	@GetMapping("/new")
    public ModelAndView createPlayerForm() {
        ModelAndView res = new ModelAndView(CREATE_UPDATE_PLAYER);
        Player player = new Player();       
        res.addObject("player", player);                                
        return res;
    }

	@PostMapping("/new")
	public ModelAndView createPlayer(@Valid Player player, BindingResult result) {
		ModelAndView res = null;
		if (result.hasErrors()) {
			return new ModelAndView(CREATE_UPDATE_PLAYER);
		}
		else {
			res = new ModelAndView("welcome");
			playerService.savePlayer(player);
			res.addObject("message", "Player successfully created!");
		}
		return res;
	}

    /* 
	@GetMapping("/{username}/edit")
    public ModelAndView editPlayerForm(@PathVariable("username") String username) {
		ModelAndView res = new ModelAndView(CREATE_USER);
        User user = userService.getUserByUsername(username);       
        res.addObject("user", user);
        return res;
    }

	@PostMapping("/{username}/edit")
    public ModelAndView editPlayer(@PathVariable("username")String username, @Valid User user, BindingResult br) {
        ModelAndView res = new ModelAndView("welcome");
        if (br.hasErrors()) {
            return new ModelAndView(CREATE_USER, br.getModel());
        }
        User userToBeUpdated = userService.getUserByUsername(username); 
        BeanUtils.copyProperties(user, userToBeUpdated,"id", "online", "playing");
        userService.saveUser(userToBeUpdated);
        res.addObject("message", "Player edited succesfully!");
        return res;
    }*/

    @GetMapping("/{username}/edit")
    public ModelAndView editPlayerForm(@PathVariable("username") String username) {
		ModelAndView res = new ModelAndView(CREATE_UPDATE_PLAYER);
        Player player = playerService.getPlayerByUsername(username);        
        res.addObject("player", player);
        return res;
    }

	@PostMapping("/{username}/edit")
    public ModelAndView editPlayer(@PathVariable("username")String username, @Valid Player player, BindingResult br) {
        ModelAndView res = new ModelAndView("welcome");
        if (br.hasErrors()) {
            return new ModelAndView(CREATE_UPDATE_PLAYER, br.getModel());
        }
        Player playerToBeUpdated = playerService.getPlayerByUsername(username); 
        BeanUtils.copyProperties(player, playerToBeUpdated,"id", "online", "playing");
        playerService.saveEditedPlayer(playerToBeUpdated);
        res.addObject("message", "Player edited succesfully!");
        return res;
    }

	@GetMapping("/{username}/delete")
    public String removeUser(@PathVariable("username") String username, ModelMap model){
        String message;

        try{
            userService.removeUser(username);
            message = "User " + username + " successfully deleted";   
        }catch (EmptyResultDataAccessException e){
            message = "User " + username + " doesn't exist";
        }
        model.put("message", message);
     	model.put("messageType", "info");
     	return listAllUsers(model);
    }
}

