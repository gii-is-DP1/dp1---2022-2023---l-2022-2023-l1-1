package org.springframework.samples.petclinic.progress;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.player.Player;
import org.springframework.stereotype.Service;

@Service
public class ProgressService {
    
    ProgressRepository progressRepository;

    @Autowired
    public ProgressService(ProgressRepository progressRepository) {
        this.progressRepository = progressRepository;
    }

    public List<Progress> getProgress() {
        return progressRepository.findAll();
    }

    public List<Progress> getUserProgress(Player player) {
        return progressRepository.findByPlayer(player);
    }
}
