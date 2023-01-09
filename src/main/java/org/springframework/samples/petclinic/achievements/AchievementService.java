package org.springframework.samples.petclinic.achievements;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.enums.State;
import org.springframework.samples.petclinic.game.Game;
import org.springframework.samples.petclinic.game.GameService;
import org.springframework.samples.petclinic.player.PlayerService;
import org.springframework.samples.petclinic.user.User;
import org.springframework.samples.petclinic.user.UserService;
import org.springframework.stereotype.Service;

@Service
public class AchievementService {
    
    AchievementRepository achievementRepository;

    @Autowired
    private PlayerService playerService;
    @Autowired
    private UserService userService;
    @Autowired
    private GameService gameService;

    @Autowired
    public AchievementService(AchievementRepository achievementRepository) {
        this.achievementRepository = achievementRepository;
    }

    public List<Achievement> getAchievements() {
        return achievementRepository.findAll();
    }

    public Achievement getById (int id) {
        return achievementRepository.findById(id).get();
    }

    public void deleteAchievementById (int id) {
        achievementRepository.deleteById(id);
    }

    public void saveAchievement (Achievement achievement) { 
        achievementRepository.save(achievement);
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
