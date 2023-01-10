package org.springframework.samples.petclinic.achievements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.deck.DeckService;
import org.springframework.samples.petclinic.enums.State;
import org.springframework.samples.petclinic.game.Game;
import org.springframework.samples.petclinic.game.GameService;
import org.springframework.samples.petclinic.player.Player;
import org.springframework.samples.petclinic.player.PlayerService;
import org.springframework.samples.petclinic.user.User;
import org.springframework.samples.petclinic.user.UserService;
import org.springframework.stereotype.Service;

@Service
public class StatisticsService {

    @Autowired
    private PlayerService playerService;
    @Autowired
    private UserService userService;
    @Autowired
    private GameService gameService;
    @Autowired
	private DeckService deckService;

    @Transactional
    public Map<User, Double> listRankingUserVictory() throws DataAccessException{

        List<User> list =new ArrayList<User>();
        Map<User,Double> result = new HashMap();
        List<Double> victoryList = new ArrayList<Double>();
        List<Game> allFinishedGames = gameService.getGamesByState(State.FINISHED);
        List<User> usersList = (List<User>) userService.findAll();
        for (User u: usersList){
            Double victory = playerService.findUserWins(u, allFinishedGames);
            if (victoryList.size() < 10){
                victoryList.add(victory);
                list.add(u);
            } else if (victoryList.size() == 10) {
                int i = 0;
                for (Double v: victoryList){
                    if (victory > v){
                        list.remove(i);
                        victoryList.remove(i);
                        list.add(u);
                        victoryList.add(victory);
                    }
                    i += 1;
                }
            }
        }
        List<Double> orderedVictoryList = orderedVictories(victoryList);
        List<User> orderedUsers = orderedUsers(list, victoryList, orderedVictoryList);

        for (int i = 0; i < orderedUsers.size() + 1; i++){
            result.put(orderedUsers.get(i), orderedVictoryList.get(i));
        }
        return result;

    }
    
    @Transactional
    public List<Double> orderedVictories(List<Double> victoryList) throws DataAccessException{
        List<Double> result = new ArrayList<Double>();
        Double x = 0.;
        Integer i = 0;
        while (i < victoryList.size() + 1){
            for (Double v: victoryList){
                if (v > x){
                    x = v;
                }
            }
            result.add(x);
            victoryList.remove(victoryList.indexOf(x));
        }
        return result;
    }

    @Transactional
    public List<User> orderedUsers(List<User> listUsers, List<Double> victoryList, List<Double> orderedVictories){
        List<User> result = new ArrayList<User>();
        for (Double v: orderedVictories){
            result.add(listUsers.get(victoryList.indexOf(v)));
        }
        return result;
    }
    
    @Transactional
    public List<Double> listStatistics(User user) throws DataAccessException{
        List<Double> list =new ArrayList<Double>();
        
		Double gamesPlayed = (double) userService.matchesPlayedForUser(user);
        
        List<Game> allFinishedGames = gameService.getGamesByState(State.FINISHED);
        
        Double victory = playerService.findUserWins(user, allFinishedGames);
        
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
		list.add(victory);
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
