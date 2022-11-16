package org.springframework.samples.petclinic.achievements;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AchievementService {
    
    AchievementRepository achievementRepository;

    @Autowired
    public AchievementService(AchievementRepository achievementRepository) {
        this.achievementRepository= achievementRepository;
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

}
