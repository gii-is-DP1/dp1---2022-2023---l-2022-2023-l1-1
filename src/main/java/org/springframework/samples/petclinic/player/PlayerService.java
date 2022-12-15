package org.springframework.samples.petclinic.player;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.user.AuthoritiesService;
import org.springframework.samples.petclinic.user.User;
import org.springframework.samples.petclinic.user.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PlayerService {

	@Autowired
    private PlayerRepository playerRepository;

    @Autowired
	private UserService userService;

    @Autowired
	private AuthoritiesService authoritiesService;

	@Autowired
	public PlayerService(PlayerRepository playerRepository) {
		this.playerRepository = playerRepository;
	}

	@Transactional
	public List<Player> getAll(){
		return playerRepository.findAll();
	}

	@Transactional
	public Player savePlayer(Player player) throws DataAccessException {
		userService.saveUser(player.getUser());
		authoritiesService.saveAuthorities(player.getUser().getUsername(), "player");
		player.setOnline(false);
		player.setPlaying(false);
		playerRepository.save(player);
		return player;
	}

	@Transactional
	public Player saveEditedPlayer(Player player) throws DataAccessException {
		userService.saveUser(player.getUser());
		authoritiesService.saveAuthorities(player.getUser().getUsername(), "player");
		playerRepository.save(player);
		return player;
	}
	
	@Transactional
	public void deletePlayer(Integer id) throws DataAccessException {
		Player p = getPlayerById(id);
		playerRepository.delete(p);
	}

	public Player getPlayerById(Integer id) {
		return playerRepository.findById(id).get();
	}

	@Transactional(readOnly = true)
	public Player getPlayerByUsername(String username) {
		return playerRepository.findPlayerByUsername(username);

	}

}
