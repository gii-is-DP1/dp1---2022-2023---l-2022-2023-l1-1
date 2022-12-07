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
	private static final String CREATE = "/users/createUser";

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

	@GetMapping(value="/new")
    public String createUser(ModelMap modelMap) {
        String vista = "users/createUser";
        modelMap.addAttribute("user", new User());
        return vista;
    }

	@PostMapping(value="/new")
    public String saveUser(@Valid User user, BindingResult result, ModelMap modelMap) {
        if (result.hasErrors()) {
            return CREATE;
        } else {
			List<User> allUsers = new ArrayList<>();
			for (User u: userService.findAll()){
				allUsers.add(u);
			}
			this.userService.saveUser(user);
			allUsers.add(user);
            modelMap.addAttribute("users", allUsers);

            authoritiesService.saveAuthorities(user.getUsername(), "user");
            modelMap.addAttribute("message", "User created");

			Player player = new Player();
			player.setId(allUsers.size()+1);
			player.setUser(user);
			player.setOnline(true);
			player.setPlaying(false);
			playerService.savePlayer(player);

			return VIEWS_USER_LIST;
        }
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

		@GetMapping("/{username}/edit")
    	public String getUser(@PathVariable("username") String username, ModelMap model){
		
			User user = userService.findUser(username);
			model.put("user", user);
			return CREATE;
    	}

    @PostMapping("/{username}/edit")
    public String saveUser(@PathVariable("username")String username, @Valid User user, BindingResult bindingResult, ModelMap model){
        if(bindingResult.hasErrors()){
            return CREATE;
        }
        else {
            User userToUpdate = userService.findUser(username);
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

