package org.springframework.samples.petclinic.achievements;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

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
    public Map<Player, Double> listRankingUserVictory() throws DataAccessException{

        List<Player> topPlayers = new ArrayList<>();
        Map<Player, Double> result = new HashMap<>();
        List<Double> victoryList = new ArrayList<Double>();
        List<Player> players = playerRepository.findAll();

        for (Player p: players){
            Double victories = playerService.findWinsByPlayer(p);
            if (victoryList.size() < 10) {
                victoryList.add(victories);
                topPlayers.add(p);
            } else if (victoryList.size() == 10) {
                int i = 0;
                for (Double v: victoryList) {
                    if (victories > v){
                        topPlayers.remove(i);
                        victoryList.remove(i);
                        topPlayers.add(p);
                        victoryList.add(victories);
                    }
                    i += 1;
                }
            }
        }
        
        List<Double> orderedVictoryList = orderedVictories(victoryList); System.out.println(orderedVictoryList +" ))))))))) "+ victoryList);
        List<Player> orderedPlayers = orderedPlayers(topPlayers, victoryList, orderedVictoryList); 
        System.out.println(orderedPlayers.stream().map(p->p.getUser().getUsername()).collect(Collectors.toList())+
        " ((((((((((((( "+topPlayers.stream().map(p->p.getUser().getUsername()).collect(Collectors.toList()));

        for (int i = 0; i < orderedPlayers.size(); i++){
            result.put(orderedPlayers.get(i), orderedVictoryList.get(i));
        }
        return result;

    }
    
    @Transactional
    public List<Double> orderedVictories(List<Double> victoryList) throws DataAccessException{
        /* 
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
        return result;*/
        Collections.sort(victoryList, Collections.reverseOrder());
        return victoryList;
    }

    @Transactional
    public List<Player> orderedPlayers(List<Player> topPlayers, List<Double> victoryList, List<Double> orderedVictories){
        List<Player> result = new ArrayList<>();
        for (Double v: orderedVictories){
            result.add(topPlayers.get(victoryList.indexOf(v)));
        }
        return result;
    }
    
    @Transactional
    public List<Double> listStatistics(User user) throws DataAccessException{
        List<Double> list =new ArrayList<Double>();

        Player player = playerRepository.findPlayerByUsername(user.getUsername());
        
		Double gamesPlayed = playerService.getGamesPlayedByPlayer(player);
        
        List<Game> allFinishedGames = gameService.getGamesByState(State.FINISHED);
        
        Double victory = playerService.findWinsByPlayer(player);
        
        Double loss = gamesPlayed-victory;
        
        Double timePlaying = Double.valueOf(playerService.getTotalTimePlaying(user));
        
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
        Double averageTimePlaying = playerService.getTotalTimePlaying(user) == 0 ? 0.0 : timePlaying/gamesPlayed;
        Double maxTimePlaying = Double.valueOf(playerService.getMaxTimePlaying(user));
        Double minTimePlaying = Double.valueOf(playerService.getMinTimePlaying(user));
        Double globalTimePlaying = Double.valueOf(gameService.getGlobalTimePlaying());
        Double globalAverageTimePlaying = globalTimePlaying/allFinishedGames.size();
        Double globalMaxTimePlaying = Double.valueOf(gameService.getGlobalMaxTimePlaying());
        Double globalMinTimePlaying = Double.valueOf(gameService.getGlobalMinTimePlaying());
        Double winsAsTraitor = playerService.findUserWinsAsTraitor(user);
        Double perWinsAsTraitor;
        Double winsAsLoyal = playerService.findUserWinsAsLoyal(user);
        Double perWinsAsLoyal;
        Double winsAsMerchant = playerService.findUserWinsAsMerchant(user);
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
