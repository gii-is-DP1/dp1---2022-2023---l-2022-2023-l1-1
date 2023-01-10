package org.springframework.samples.petclinic.player;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.hibernate.query.criteria.internal.ValueHandlerFactory.IntegerValueHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.deck.DeckService;
import org.springframework.samples.petclinic.enums.Faction;
import org.springframework.samples.petclinic.game.Game;
import org.springframework.samples.petclinic.playerInfo.PlayerInfoRepository;
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
	private DeckService deckService;

	@Autowired
	private PlayerInfoRepository playerInfoRepository;

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
		return playerRepository.getPlayerByUsername(username);

	}

	@Transactional
	public List<Player> findbyUsernameMatchFinished(String username) throws DataAccessException {
		List<Player> players = playerRepository.findByUsername(username);
		List<Player> result = new ArrayList<Player>();
		/* 
		for(Player p : players){
			if(p.getMatch().isFinished()){
				result.add(p);
			}

		}
		*/
		return result;
	}

	@Transactional
	public Double findUserWins(User user, List<Game> allFinishedGames) {
		Double result = 0.;
		Player player = playerRepository.getPlayerByUsername(user.getUsername());
		for (Game g:allFinishedGames){
			List<Player> winners = deckService.winnerPlayers(g, g.getWinners());
			if (winners.contains(player)){
				result = result + 1.;
			}
		}
		return result;
	}

	@Transactional
	public Double findUserWinsAsTraitor(User user, List<Game> allFinishedGames) {
		Double result = 0.;
		Player player = playerRepository.getPlayerByUsername(user.getUsername());
		Faction traitor = Faction.TRAITORS;
		for (Game g:allFinishedGames){
			List<Player> winners = deckService.winnerPlayers(g, g.getWinners());
			if (winners.contains(player) && g.getWinners() == traitor){
				result = result + 1.;
			}
		}
		return result;
	}

	@Transactional
	public Double findUserWinsAsLoyal(User user, List<Game> allFinishedGames) {
		Double result = 0.;
		Player player = playerRepository.getPlayerByUsername(user.getUsername());
		Faction loyal = Faction.LOYALS;
		for (Game g:allFinishedGames){
			List<Player> winners = deckService.winnerPlayers(g, g.getWinners());
			if (winners.contains(player) && g.getWinners() == loyal){
				result = result + 1.;
			}
		}
		return result;
	}

	@Transactional
	public Double findUserWinsAsMerchant(User user, List<Game> allFinishedGames) {
		Double result = 0.;
		Player player = playerRepository.getPlayerByUsername(user.getUsername());
		Faction merchant = Faction.MERCHANTS;
		for (Game g:allFinishedGames){
			List<Player> winners = deckService.winnerPlayers(g, g.getWinners());
			if (winners.contains(player) && g.getWinners() == merchant){
				result = result + 1.;
			}
		}
		return result;
	}
	@Transactional
	public Double getTotalTimePlaying(User user, List<Game> allFinishedGames) {
		Double result = 0.;
		Player player = playerRepository.getPlayerByUsername(user.getUsername());
		
		for (Game g:allFinishedGames){
			List<Player> players = playerInfoRepository.findPlayersByGame(g);
			if (players.contains(player)){
				result = result + 1;
			}
		}
		return result;
	}

	public Double getMinTimePlaying(User user, List<Game> allFinishedGames) {
		Double result = 0.;
		Player player = playerRepository.getPlayerByUsername(user.getUsername());
		for (Game g:allFinishedGames){
			List<Player> players = playerInfoRepository.findPlayersByGame(g);
			if (players.contains(player) && 1 > result){
				result = 1.;
			}
		}
		return result;
	}

    public Double getMaxTimePlaying(User user, List<Game> allFinishedGames) {
        Double result = 999999999999999999999999999999999999999999999999999.;
		Player player = playerRepository.getPlayerByUsername(user.getUsername());
		for (Game g:allFinishedGames){
			List<Player> players = playerInfoRepository.findPlayersByGame(g);
			if (players.contains(player) && 1 < result){
				result = 1.;
			}
		}
		return result;
    }

}
