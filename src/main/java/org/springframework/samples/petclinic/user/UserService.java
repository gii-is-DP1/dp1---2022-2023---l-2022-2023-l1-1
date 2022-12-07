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
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.player.PlayerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Mostly used as a facade for all Petclinic controllers Also a placeholder
 * for @Transactional and @Cacheable annotations
 *
 * @author Michael Isvy
 */
@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PlayerService playerService;

	@Autowired
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Transactional
    public void saveUser(User user) throws DataAccessException {
        user.setEnabled(true);
        userRepository.save(user);
    }

	@Transactional
	public User findUser(String username) {
		Optional<User> u =  userRepository.findById(username);
		return u.get();
	}

	@Transactional
	public Iterable<User> findAll(){
		return userRepository.findAll();
	}

	@Transactional
	public void removeUser(String username){
		this.userRepository.deleteById(username);
	}

	/* 

	@Transactional
    public User saveUser(User user) throws DataAccessException {
		String username = user.getUsername();
        List<String> usernameList = new ArrayList<>();
        for(User u:userRepository.findAll()){
            usernameList.add(u.getUsername());
        }
        user.setEnabled(true);
        userRepository.save(user);
		return user;
    }


	public List<User> findAll() {
		return userRepository.findAll();
	}

	public Optional<User> findUser(String username) {
		return userRepository.findById(username);
	}

	

	@Transactional
	public User getUser(String username){
		Optional<User> user = this.userRepository.findById(username);
		return user.isPresent()? user.get() : null;
	}

	*/
}
