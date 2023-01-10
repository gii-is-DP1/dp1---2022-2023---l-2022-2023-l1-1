package org.springframework.samples.petclinic.achievements;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.deck.DeckService;
import org.springframework.samples.petclinic.enums.State;
import org.springframework.samples.petclinic.game.Game;
import org.springframework.samples.petclinic.game.GameService;
import org.springframework.samples.petclinic.player.Player;
import org.springframework.samples.petclinic.player.PlayerRepository;
import org.springframework.samples.petclinic.player.PlayerService;
import org.springframework.samples.petclinic.user.User;
import org.springframework.samples.petclinic.user.UserRepository;
import org.springframework.samples.petclinic.user.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StatisticsService {

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private PlayerService playerService;

    @Autowired
    private UserService userService;

    @Autowired
    private GameService gameService;

    @Autowired
	private DeckService deckService;

    @Transactional
    public Map<Player, Integer> listRankingUserVictory() throws DataAccessException {
        Map<Player, Integer> result = new LinkedHashMap<>();
        Map<Player, Integer> allPlayersAndVictories = new LinkedHashMap<>();
        List<Game> allFinishedGames = gameService.getGamesByState(State.FINISHED);
        List<Player> players = playerRepository.findAll();

        for (Player p: players){
            Integer victories = playerService.findWinsByPlayer(p, allFinishedGames);
            allPlayersAndVictories.put(p, victories);
        }

        Map<Player, Integer> sortedMap = allPlayersAndVictories.entrySet().stream()
        .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

        System.out.println("============="+sortedMap);

        int count = 0;
        for (Map.Entry<Player, Integer> entry : sortedMap.entrySet()) {
            if (count == 10) {
                break;
            }
                result.put(entry.getKey(), entry.getValue());
                count++;
            }   
            System.out.println("======== " + result);
        return result;
    }
    
    @Transactional
    public List<Double> listStatistics(User user) throws DataAccessException{
        List<Double> list =new ArrayList<Double>();

        Player player = playerRepository.findPlayerByUsername(user.getUsername());
        
		Double gamesPlayed = playerService.getGamesPlayedByPlayer(player);
        
        List<Game> allFinishedGames = gameService.getGamesByState(State.FINISHED);
        
        Integer victory = playerService.findWinsByPlayer(player, allFinishedGames);
        
        Double loss = gamesPlayed-victory;
        
        Double timePlaying = playerService.getTotalTimePlaying(user, allFinishedGames);
        
        Double PerWin;
        Double PerLos;
        if (gamesPlayed==0.0){
            PerWin=0.0;
            PerLos=0.0;
        }
        else{
            PerWin = victory*100/gamesPlayed;
            PerLos = loss*100/gamesPlayed;
        }
        Double averageTimePlaying = timePlaying/gamesPlayed;
        Double maxTimePlaying = playerService.getMaxTimePlaying(user, allFinishedGames);
        Double minTimePlaying = playerService.getMinTimePlaying(user, allFinishedGames);
        Double globalTimePlaying = gameService.getGlobalTimePlaying(allFinishedGames);
        Double globalAverageTimePlaying = globalTimePlaying/allFinishedGames.size();
        Double globalMaxTimePlaying = gameService.getGlobalMaxTimePlaying(allFinishedGames);
        Double globalMinTimePlaying = gameService.getGlobalMinTimePlaying(allFinishedGames);
        Double winsAsTraitor = playerService.findUserWinsAsTraitor(user, allFinishedGames);
        Double perWinsAsTraitor;
        Double winsAsLoyal = playerService.findUserWinsAsLoyal(user, allFinishedGames);
        Double perWinsAsLoyal;
        Double winsAsMerchant = playerService.findUserWinsAsMerchant(user, allFinishedGames);
        Double perWinsAsMerchant;
        if (winsAsTraitor == 0.0){
            perWinsAsTraitor = 0.0;
        } else {
            perWinsAsTraitor = winsAsTraitor*100/victory;
        }
        if (winsAsLoyal == 0.0){
            perWinsAsLoyal = 0.0;
        } else {
            perWinsAsLoyal = winsAsTraitor*100/victory;
        }if (winsAsMerchant == 0.0){
            perWinsAsMerchant = 0.0;
        } else {
            perWinsAsMerchant = winsAsMerchant*100/victory;
        }
		list.add(gamesPlayed);
		list.add((double) victory);
        list.add(loss);
		list.add(PerWin);
        list.add(PerLos);
        list.add(timePlaying);
        list.add(globalTimePlaying);
        list.add(averageTimePlaying);
        list.add(globalAverageTimePlaying);
        list.add(maxTimePlaying);
        list.add(globalMaxTimePlaying);
        list.add(minTimePlaying);
        list.add(globalMinTimePlaying);
        list.add(winsAsLoyal);
        list.add(perWinsAsLoyal);
        list.add(winsAsMerchant);
        list.add(perWinsAsMerchant);
        list.add(winsAsTraitor);
        list.add(perWinsAsTraitor);
		return list;
    }

}
