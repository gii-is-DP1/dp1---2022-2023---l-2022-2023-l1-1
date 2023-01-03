package org.springframework.samples.petclinic.achievements;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
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
        Double victory = playerService.findUserWins(user, true);
        Double loss = gamesPlayed-victory;
        Double PorWin;
        Double PorLos;
        if (gamesPlayed==0.0){
            PorWin=0.0;
            PorLos=0.0;
        }
        else{
            PorWin = victory*100/gamesPlayed;
            PorLos = loss*100/gamesPlayed;
        }
		list.add(gamesPlayed);
		list.add(victory);
        list.add(loss);
		list.add(PorWin);
        list.add(PorLos);
		return list;
    }

}
