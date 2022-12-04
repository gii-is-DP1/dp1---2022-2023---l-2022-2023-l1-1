package org.springframework.samples.petclinic.progress;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.achievements.Achievement;
import org.springframework.samples.petclinic.achievements.AchievementRepository;
import org.springframework.samples.petclinic.player.Player;
import org.springframework.samples.petclinic.player.PlayerRepository;
import org.springframework.stereotype.Service;

@Service
public class ProgressService {
    
    ProgressRepository progressRepository;
    PlayerRepository playerRepository;
    AchievementRepository achievementRepository;

    @Autowired
    public ProgressService(ProgressRepository progressRepository, PlayerRepository playerRepository, AchievementRepository achievementRepository) {
        this.progressRepository = progressRepository;
        this.playerRepository = playerRepository;
        this.achievementRepository = achievementRepository;
    }

    public List<Progress> getProgress() {
        return progressRepository.findAll();
    }

    public List<Progress> getPlayerProgress(Player player) {
        return progressRepository.findByPlayer(player);
    }

    /*public List<Progress> addNewAchievement(Achievement achievement) { //CREO QUE NO HACE FALTA
        List<Progress> progressCreated = new ArrayList<>();
        for (Player player :  playerRepository.findAll()){
            Progress progress = new Progress(0.0, achievement, player);
            progressCreated.add(progress);
        }
        return progressCreated;
    } */

    public void addAchievementPlayer (Achievement achievement, Player player) {
        progressRepository.save(new Progress(0.0, achievement, player));
    }

    public void saveProgress (Progress progress) { 
        progressRepository.save(progress);
    }
}
