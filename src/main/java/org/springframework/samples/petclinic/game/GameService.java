package org.springframework.samples.petclinic.game;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.enums.State;
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
    public List<Game> getGamesByNameAndState(String name, State s) {
        return repo.findByName(name).stream().filter(g -> g.getState() == s).collect(Collectors.toList());
    }
    @Transactional(readOnly = true)
    public Game getGameById(Integer id){
        return repo.findById(id);
    }
}
