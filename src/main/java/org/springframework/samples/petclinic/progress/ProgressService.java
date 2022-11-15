package org.springframework.samples.petclinic.progress;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.achievements.Achievement;
import org.springframework.samples.petclinic.player.Player;
import org.springframework.samples.petclinic.player.PlayerRepository;
import org.springframework.stereotype.Service;

@Service
public class ProgressService {
    
    ProgressRepository progressRepository;
    PlayerRepository playerRepository;

    @Autowired
    public ProgressService(ProgressRepository progressRepository, PlayerRepository playerRepository) {
        this.progressRepository = progressRepository;
        this.playerRepository = playerRepository;
    }

    public List<Progress> getProgress() {
        return progressRepository.findAll();
    }

    public List<Progress> getUserProgress(Player player) {
        return progressRepository.findByPlayer(player);
    }

    public List<Progress> addNewAchievement(Achievement achievement) {
        List<Progress> progressCreated = new ArrayList<>();
        for (Player player :  playerRepository.findAll()){
            Progress progress = new Progress(0.0, achievement, player);
            progressCreated.add(progress);
        }
        return progressCreated;
    }

    public void saveProgress (Progress progress) { 
        progressRepository.save(progress);
    }
}
