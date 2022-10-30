package org.springframework.samples.petclinic.game;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.enums.State;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/games")
public class GameController {

    private static final String GAMES_LIST = "/games/gamesList";
    private static final String CREATE_GAMES = "/games/createGame";
    
    private GameService service;

    @Autowired
    public GameController(GameService service) {
        this.service = service;
    }

    @Transactional(readOnly = true)
    @GetMapping("/history")
    public ModelAndView showGamesHistory() {
        ModelAndView res = new ModelAndView(GAMES_LIST);
        res.addObject("games", service.getGames(State.FINISHED));
        return res;
    }

    @Transactional(readOnly = true)
    @GetMapping("/inProcess")
    public ModelAndView showGamesInProcess() {
        ModelAndView res = new ModelAndView(GAMES_LIST);
        res.addObject("games", service.getGames(State.IN_PROCESS));
        return res;
    }

    @Transactional(readOnly = true)
    @GetMapping("/starting")
    public ModelAndView showGamesStarting() {
        ModelAndView res = new ModelAndView(GAMES_LIST);
        res.addObject("games", service.getGames(State.STARTING));
        return res;
    }

    @Transactional
    @GetMapping("/create")
    public ModelAndView createGame() {
        ModelAndView res = new ModelAndView(CREATE_GAMES);
        Game game =new Game();         
        res.addObject("game", game);                                  
        return res;
    }
}
