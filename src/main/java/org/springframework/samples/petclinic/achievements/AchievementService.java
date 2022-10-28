package org.springframework.samples.petclinic.achievements;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AchievementService {
    
    AchievementRepository rep;

    @Autowired
    public AchievementService(AchievementRepository rep) {
        this.rep= rep;
    }

    List<Achievement> getAchievements() {
        return rep.findAll();
    }

    public Achievement getById (int id) {
        return rep.findById(id).get();
    }

    public void deleteAchievementById (int id) {
        rep.deleteById(id);
    }

    public void saveAchievement (Achievement achievement) { 
        rep.save(achievement);
    }

}
