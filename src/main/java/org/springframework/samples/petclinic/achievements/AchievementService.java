package org.springframework.samples.petclinic.achievements;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.enums.AchievementType;
import org.springframework.samples.petclinic.invitation.InvitationRepository;
import org.springframework.samples.petclinic.invitation.InvitationService;
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

    public List<AchievementType> getAllTypes () {
        return List.of(AchievementType.values());
    }

}
