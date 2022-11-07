package org.springframework.samples.petclinic.player;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.user.AuthoritiesService;
import org.springframework.samples.petclinic.user.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PlayerService {

    private PlayerRepository playerRepository;

    @Autowired
	private UserService userService;

    @Autowired
	private AuthoritiesService authoritiesService;

	@Autowired
	public PlayerService(PlayerRepository playerRepository) {
		this.playerRepository = playerRepository;
	}

    public List<Player> getAll(){
        return playerRepository.findAll();
    }

    @Transactional
	public void savePlayer(Player p) throws DataAccessException {
		//creating player
		playerRepository.save(p);		
		//creating user
		userService.saveUser(p.getUser());
		//creating authorities
		authoritiesService.saveAuthorities(p.getUser().getUsername(), "player");
	}	
}
