package org.springframework.samples.petclinic.game;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.enums.State;
import org.springframework.samples.petclinic.playerInfo.PlayerInfoService;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
//PRUEBA GITHUB
@Controller
@RequestMapping("/games")
public class GameController {

    private static final String GAMES_LIST = "/games/gamesList";
    private static final String FIND_GAMES_HISTORY = "/games/findGamesHistory";
    private static final String FIND_GAMES_IN_PROCESS = "/games/findGamesInProcess";
    private static final String FIND_GAMES_STARTING = "/games/findGamesStarting";
    private static final String CREATE_GAME = "/games/createGame";
	private static final String GAME_LOBBY = "/games/gameLobby";

    @Autowired
    private GameService gameService;
	@Autowired
	private PlayerInfoService playerInfoService;

    @Autowired
    public GameController(GameService service) {
        this.gameService = service;
    }

    @Transactional(readOnly = true)
    @GetMapping(value = "/history/find")
	public String gamesHistoryForm(Map<String, Object> model) {
		model.put("game", new Game());
		return FIND_GAMES_HISTORY;
	}

    @Transactional(readOnly = true)
    @GetMapping(value = "/history")
	public ModelAndView processGamesHistoryForm(Game game, BindingResult result) {

		// allow parameterless GET request for /games to return all records
		if (game.getName() == null) {
			game.setName(""); // empty string signifies broadest possible search
		}

		// find games by name
		List<Game> results = this.gameService.getGamesByNameAndState(game.getName(), State.FINISHED);
		if (results.isEmpty()) {
			// no games found
			result.rejectValue("name", "notFound", "not found");
			return new ModelAndView(FIND_GAMES_HISTORY);
		}
		else {
			// games found
			ModelAndView res = new ModelAndView(GAMES_LIST);
            res.addObject("games", results); 
			return res;
		}
	}

    @Transactional(readOnly = true)
    @GetMapping(value = "/inProcess/find")
	public String gamesInProcessForm(Map<String, Object> model) {
		model.put("game", new Game());
		return FIND_GAMES_IN_PROCESS;
	}

    @Transactional(readOnly = true)
    @GetMapping(value = "/inProcess")
	public ModelAndView processGamesInProcessForm(Game game, BindingResult result) {
		if (game.getName() == null) {
			game.setName("");
		}

		List<Game> results = this.gameService.getGamesByNameAndState(game.getName(), State.IN_PROCESS);
		if (results.isEmpty()) {
			result.rejectValue("name", "notFound", "not found");
			return new ModelAndView(FIND_GAMES_IN_PROCESS);
		}
		else {
			ModelAndView res = new ModelAndView(GAMES_LIST);
            res.addObject("games", results); 
			return res;
		}
	}

    @Transactional(readOnly = true)
    @GetMapping(value = "/starting/find")
	public String gamesStartingForm(Map<String, Object> model) {
		model.put("game", new Game());
		return FIND_GAMES_STARTING;
	}

    @Transactional(readOnly = true)
    @GetMapping(value = "/starting")
	public ModelAndView processGamesStartingForm(Game game, BindingResult result) {
		if (game.getName() == null) {
			game.setName("");
		}

		List<Game> results = this.gameService.getGamesByNameAndState(game.getName(), State.STARTING);
		if (results.isEmpty()) {
			result.rejectValue("name", "notFound", "not found");
			return new ModelAndView(FIND_GAMES_STARTING);
		}
		else {
			ModelAndView res = new ModelAndView(GAMES_LIST);
            res.addObject("games", results); 
			return res;
		}
	}

    @Transactional
    @GetMapping("/create")
    public ModelAndView createGame() {
        ModelAndView res = new ModelAndView(CREATE_GAME);
        Game game =new Game();         
        res.addObject("game", game);                                  
        return res;
    }

	@Transactional
	@GetMapping("/{id}/lobby")
	public ModelAndView showlobby(@PathVariable("gameId") int gameId){
		ModelAndView res=new ModelAndView(GAME_LOBBY);
		Game game=gameService.getGameById(gameId);
		res.addObject("playerInfos", playerInfoService.getPlayerByGame(game));
		return res;
	}
}