package org.springframework.samples.petclinic.player;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.hibernate.envers.RevisionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.deck.DeckRepository;
import org.springframework.samples.petclinic.deck.FactionCard;
import org.springframework.samples.petclinic.deck.FactionCardRepository;
import org.springframework.samples.petclinic.enums.Faction;
import org.springframework.samples.petclinic.game.Game;
import org.springframework.data.history.Revision;
import org.springframework.data.history.Revisions;
import org.springframework.samples.petclinic.deck.DeckRepository;
import org.springframework.samples.petclinic.game.Game;
import org.springframework.samples.petclinic.game.GameRepository;
import org.springframework.samples.petclinic.model.audit.UserRevEntity;
import org.springframework.samples.petclinic.player.exceptions.DuplicatedUsernameException;
import org.springframework.samples.petclinic.playerInfo.PlayerInfoRepository;
import org.springframework.samples.petclinic.user.AuthoritiesService;
import org.springframework.samples.petclinic.user.User;
import org.springframework.samples.petclinic.user.UserRepository;
import org.springframework.samples.petclinic.user.UserService;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PlayerService {

	@Autowired
    private PlayerRepository playerRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PlayerInfoRepository playerInfoRepository;

	@Autowired
	private DeckRepository deckRepository;

	@Autowired
	private SessionRegistry sessionRegistry;

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

	@Transactional(readOnly = true)
	public Player getPlayerById(Integer id) {
		return playerRepository.findById(id).get();
	}

	@Transactional(readOnly = true)
	public Player getPlayerByUsername(String username) {
		return playerRepository.findPlayerByUsername(username);
	}

	@Transactional(readOnly = true)
	public List<String> getAllUsernames() {
		return playerRepository.findAllUsernames();
	}

	@Transactional(readOnly = true)
	private Boolean duplicatedUsername(String username) {
        List<String> usernames = playerRepository.findAllUsernames();
        return usernames.contains(username);
    }

	@Transactional(rollbackFor = DuplicatedUsernameException.class)
	public Player savePlayer(Player player) throws DataAccessException, DuplicatedUsernameException {
		if(duplicatedUsername(player.getUser().getUsername())){
			throw new DuplicatedUsernameException();
		} else {
			userService.saveUser(player.getUser());
			authoritiesService.saveAuthorities(player.getUser().getUsername(), "player");
			player.setOnline(false);
			player.setPlaying(false);
			playerRepository.save(player);
			return player;
		}
		
	}

	@Transactional
	public Player saveEditedPlayer(Player player) throws DataAccessException {
		userService.saveUser(player.getUser());
		authoritiesService.saveAuthorities(player.getUser().getUsername(), "player");
		playerRepository.save(player);
		return player;
	}
	
	@Transactional
	public void deletePlayer(Player player) throws DataAccessException {
		playerRepository.delete(player);
	}

	@Transactional(readOnly = true)
	public Boolean hasGamesPlayed(Player player) {
		Boolean res = false;
		if(!playerInfoRepository.findGamesByPlayer(player).isEmpty() || !deckRepository.findPlayerDecks(null).isEmpty()) {
			res = true;
		}
		return res;
	}

	@Transactional
	public void checkOnlineStatus() {
		List<Object> principals = sessionRegistry.getAllPrincipals();
		for(Object o: principals) {
			List<SessionInformation> activeSessions = sessionRegistry.getAllSessions(o, false);
			UserDetails userDetails = (UserDetails) o;
			User user = userRepository.findById(userDetails.getUsername()).get();
			List<User> playerUsers = userRepository.findUserWithAuthority("player");
			if(playerUsers.contains(user)) {
				Player player = playerRepository.findPlayerByUsername(userDetails.getUsername());
				if(!activeSessions.isEmpty()) {
					player.setOnline(true);
				} else {
					player.setOnline(false);
				}
				playerRepository.save(player);
			}
		}
	}

	@Transactional(readOnly = true)
	public List<String> auditPlayer(Player player) {
		List<String> res = new ArrayList<>();
		List<String> updates = new ArrayList<>();
		List<Revision<Integer, Player>> revs = playerRepository.findRevisions(player.getId()).get().collect(Collectors.toList());
		for(Revision<Integer, Player> r: revs) {
			Instant instant = r.getRevisionInstant().get().atZone(ZoneId.systemDefault()).toInstant();
			if(r.getMetadata().getRevisionType() == org.springframework.data.history.RevisionMetadata.RevisionType.INSERT){
				res.add("Player created at " + fromInstantToDate(instant));
			} 
			else if(r.getMetadata().getRevisionType() == org.springframework.data.history.RevisionMetadata.RevisionType.UPDATE) {
				if(updates.size() % 2 == 0) {
					updates.add("Started playing a game at " + fromInstantToDate(instant));
				} 
				else {
					updates.add("Finished playing a game at " + fromInstantToDate(instant));
				}
			}
		}
		res.addAll(updates);
		return res;
	}

	public String fromInstantToDate(Instant instant) {
		Date date = Date.from(instant);
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		return formatter.format(date);
	}

}
