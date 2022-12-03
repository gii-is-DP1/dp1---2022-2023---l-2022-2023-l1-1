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

import java.util.List;
import java.util.Map;

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

/**
 * @author Juergen Hoeller
 * @author Ken Krebs
 * @author Arjen Poutsma
 * @author Michael Isvy
 */
@Controller
@RequestMapping("/users")
public class UserController {

	private static final String CREATE_PLAYER = "/users/createPlayer";
	private static final String VIEWS_USER_LIST = "/users/usersList";

	private final PlayerService service;

    private UserService userService;

	@Autowired
	public UserController(PlayerService pS) {
		this.service = pS;
	}

	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	@GetMapping
    public String listAllUsers(ModelMap model){
        List<User> allUsers = userService.findAll();
        model.put("users", allUsers);
        return VIEWS_USER_LIST;
    }

	@GetMapping(value = "/create")
	public String addUser(ModelMap model) {
		User user = new User();
		model.put("user", user);
		return CREATE_PLAYER;
	}

	@PostMapping(value = "/create")
	public String processCreationForm(@Valid User user, BindingResult result, ModelMap model) {
		if (result.hasErrors()) {
			return CREATE_PLAYER;
		}
		else {
			User newUser = new User();
        	BeanUtils.copyProperties(user, newUser);
        	User createdUser = userService.saveUser(newUser);
        	model.put("message", "User " + createdUser.getUsername() + " successfully created" );
       		return "redirect:/users/";
		}
	}

    @GetMapping(value = "/new")
	public String initCreationForm(Map<String, Object> model) {
		Player player = new Player();
		model.put("player", player);
		return CREATE_PLAYER;
	}

    @PostMapping(value = "/new")
	public String processCreationForm(@Valid Player player, BindingResult result) {
		if (result.hasErrors()) {
			return CREATE_PLAYER;
		}
		else {
			//creating player, user, and authority
			this.service.savePlayer(player);
			return "redirect:/";
		}
	}

	@GetMapping("/{username}/delete")
    public String removeUser(@PathVariable("username") String username, ModelMap model){
        String message;

        try{
            userService.removeUser(username);
            message = "Player " + username + " successfully deleted";   
        } catch (EmptyResultDataAccessException e){
            message = "Player " + username + " doesn't exist";
        }
        model.put("message", message);
        model.put("messageType", "info");
        return listAllUsers(model);
    }

	@GetMapping("/{username}/edit")
    public String getUser(@PathVariable("username") String username, ModelMap model){
		/*
		 * User user = service.getUser(username);
        	if(user !=null){
            model.put("user", user);
            return CREATE_PLAYER;
        } else {
            model.put("message", "The player " + username + " doesn't exist");
            model.put("messageType", "info");
            return listAllUsers(model);
        }
		 */
		User user = userService.getUser(username);
		model.put("user", user);
		return CREATE_PLAYER;
    }

    @PostMapping("/{username}/edit")
    public String saveUser(@PathVariable("username")String username, @Valid User user, BindingResult bindingResult, ModelMap model){
        if(bindingResult.hasErrors()){
            return CREATE_PLAYER;
        }
        else {
            User userToUpdate = userService.getUser(username);
            if(userToUpdate != null){
                BeanUtils.copyProperties(user, userToUpdate, "username");
                model.put("message", "Player " + username + " successfully updated");
                userService.saveUser(userToUpdate);
                return listAllUsers(model);
            }
            else{
                model.put("message", "Player " + username + " doesn't exist");
                model.put("messageType", "info");
                return listAllUsers(model);
            }
        }
    }

}
