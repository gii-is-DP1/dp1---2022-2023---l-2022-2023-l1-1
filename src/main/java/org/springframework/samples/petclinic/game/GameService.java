package org.springframework.samples.petclinic.game;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GameService {
    
    private GameRepository repo;

    @Autowired
    public GameService(GameRepository repo) {
        this.repo = repo;
    }

    @Transactional(readOnly = true)
    public List<Game> getGames() {
        return repo.findAll();
    }
}
