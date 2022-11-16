package org.springframework.samples.petclinic.player;

import java.util.List;
import java.util.Optional;

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
	public void removePlayer(Integer id){
		this.playerRepository.deleteById(id);
    
	public Player getPlayerById(Integer id) {
		return playerRepository.findById(id).get();
	}

    @Transactional
	public Player savePlayer(Player p) throws DataAccessException {
		//creating user
		userService.saveUser(p.getUser());
		//creating authorities
		authoritiesService.saveAuthorities(p.getUser().getUsername(), "player");

		//creating player
		return playerRepository.save(p);	
	}
	
	@Transactional
	public Player getPlayer(Integer id){
		Optional<Player> player = this.playerRepository.findById(id);
		return player.isPresent()? player.get() : null;

	}
	
	@Transactional(readOnly = true)
	public Player getPlayerByUsername(String username) {
		return playerRepository.getPlayerByUsername(username);

	}
}
