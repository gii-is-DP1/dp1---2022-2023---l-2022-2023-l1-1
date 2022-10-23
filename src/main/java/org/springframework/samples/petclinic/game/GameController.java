package org.springframework.samples.petclinic.game;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/games")
public class GameController {

    private static final String GAMES_LIST = "/games/gamesList";
    
    private GameService service;

    @Autowired
    public GameController(GameService service) {
        this.service = service;
    }

    @Transactional(readOnly = true)
    @GetMapping
    public ModelAndView showGames() {
        ModelAndView res = new ModelAndView(GAMES_LIST);
        res.addObject("games", service.getGames());
        return res;
    }
}
