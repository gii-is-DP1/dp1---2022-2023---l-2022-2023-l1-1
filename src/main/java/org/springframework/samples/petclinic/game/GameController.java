package org.springframework.samples.petclinic.game;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.deck.Deck;
import org.springframework.samples.petclinic.deck.DeckService;
import org.springframework.samples.petclinic.deck.FactionCard;
import org.springframework.samples.petclinic.deck.FactionCardService;
import org.springframework.samples.petclinic.deck.FactionCard.FCType;
import org.springframework.samples.petclinic.deck.VoteCard.VCType;
import org.springframework.samples.petclinic.enums.CurrentStage;
import org.springframework.samples.petclinic.enums.State;
import org.springframework.samples.petclinic.player.Player;
import org.springframework.samples.petclinic.player.PlayerService;
import org.springframework.samples.petclinic.playerInfo.PlayerInfo;
import org.springframework.samples.petclinic.playerInfo.PlayerInfoRepository;
import org.springframework.samples.petclinic.playerInfo.PlayerInfoService;
import org.springframework.samples.petclinic.round.Round;
import org.springframework.samples.petclinic.round.RoundController;
import org.springframework.samples.petclinic.round.RoundRepository;
import org.springframework.samples.petclinic.round.RoundService;
import org.springframework.samples.petclinic.stage.Stage;
import org.springframework.samples.petclinic.stage.StageRepository;
import org.springframework.samples.petclinic.stage.StageService;
import org.springframework.samples.petclinic.suffragiumCard.SuffragiumCard;
import org.springframework.samples.petclinic.suffragiumCard.SuffragiumCardService;
import org.springframework.samples.petclinic.turn.Turn;
import org.springframework.samples.petclinic.turn.TurnService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/games")
public class GameController {

    private static final String GAMES_LIST = "/games/gamesList";
	private static final String GAMES_FINISHED_LIST = "/games/gamesFinishedList";
    private static final String FIND_GAMES_HISTORY = "/games/findGamesHistory";
	private static final String FIND_GAMES_PLAYER_HISTORY = "/games/findGamesPlayerHistory";
    private static final String FIND_GAMES_IN_PROCESS = "/games/findGamesInProcess";
    private static final String FIND_GAMES_STARTING = "/games/findGamesStarting";
    private static final String CREATE_GAME = "/games/createGame";
	private static final String GAME_LOBBY = "/games/gameLobby";
	private static final String GAME = "/games/game";


    @Autowired
    private GameService gameService;

	@Autowired
	private PlayerService playerService;

	@Autowired
	private PlayerInfoService playerInfoService;

	@Autowired
	private SuffragiumCardService suffragiumCardService;

	@Autowired
	private DeckService deckService;

	@Autowired
	private FactionCardService factionCardService;

	@Autowired
	private RoundService roundService;

	@Autowired
	private TurnService turnService;

	@Autowired
	private StageService stageService;

    @Autowired
    public GameController(GameService service) {
        this.gameService = service;
    }

	public static List<Game> getListIntersection(List<Game> firstList, List<Game> secondList) {
        List<Game> resultList = new ArrayList<Game>();
        List <Game> result = new ArrayList <Game> (firstList);  
        HashSet <Game> othHash = new HashSet <Game> (secondList); 
        Iterator <Game> iter = result.iterator();
        while(iter.hasNext()) {
            if(!othHash.contains(iter.next())) {  
                iter.remove();            
            }     
        }
        resultList = new ArrayList<Game>(result);
        return resultList;
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
			ModelAndView res = new ModelAndView(GAMES_FINISHED_LIST);
			res.addObject("returnButton", "/games/history/find");
            res.addObject("games", results); 
			return res;
		}
	}

	@Transactional(readOnly = true)
    @GetMapping(value = "/playerHistory/find")
	public String gamesHistoryByPlayerForm(Map<String, Object> model) {
		model.put("game", new Game());
		return FIND_GAMES_PLAYER_HISTORY;
	}

    @Transactional(readOnly = true)
    @GetMapping(value = "/playerHistory")
	public ModelAndView processGamesHistoryByPlayerForm(@AuthenticationPrincipal UserDetails user, Game game, BindingResult result) {
		if (game.getName() == null) {
			game.setName("");
		}

		Player player = playerService.getPlayerByUsername(user.getUsername());
		List<Game> l1 = this.gameService.getGamesByNameAndState(game.getName(), State.FINISHED);
		List<Game> l2 = this.playerInfoService.getGamesByPlayer(player);
		List<Game> results = getListIntersection(l1, l2);
		if (results.isEmpty()) {
			result.rejectValue("name", "notFound", "not found");
			return new ModelAndView(FIND_GAMES_PLAYER_HISTORY);
		}
		else {
			ModelAndView res = new ModelAndView(GAMES_FINISHED_LIST);
			res.addObject("returnButton", "/games/playerHistory/find");
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
			res.addObject("returnButton", "/games/inProcess/find");
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
			res.addObject("returnButton", "/games/starting/find");
            res.addObject("games", results); 
			return res;
		}
	}

    @Transactional
    @GetMapping("/create")
    public ModelAndView createGameForm() {
        ModelAndView res = new ModelAndView(CREATE_GAME);
        Game game = new Game();       
        res.addObject("game", game);                                
        return res;
    }

	@Transactional
	@PostMapping("/create")
	public String createGame(@AuthenticationPrincipal UserDetails user, @Valid PlayerInfo creatorInfo, 
	@Valid Game game, BindingResult br, ModelMap model) {
		if(br.hasErrors()) {
			model.put("game", game);
			return "redirect:/games/" + game.getId().toString() + "/lobby";
		} else {
			Game newGame = gameService.saveGame(game);
			Player creator = playerService.getPlayerByUsername(user.getUsername());
			Round round = new Round(newGame);
			Turn turn = new Turn(round);
			Stage stage = new Stage(turn);

			playerInfoService.saveCreatorInfo(creatorInfo, game, creator);
			roundService.save(round);
			turnService.save(turn);
			stageService.save(stage);
			
			model.put("game", game);
        	model.put("playerInfos", playerInfoService.getPlayerInfosByGame(game));
			model.put("message", "Game successfully created!");
		}
		return "redirect:/games/" + game.getId().toString() + "/lobby";
	}

	@Transactional
    @GetMapping("/{gameId}/lobby")
    public ModelAndView showLobby(@PathVariable("gameId") Integer gameId, HttpServletResponse response){
		response.addHeader("Refresh", "2");
        ModelAndView res=new ModelAndView(GAME_LOBBY);
        Game game=gameService.getGameById(gameId);
        res.addObject("game", game);
        res.addObject("playerInfos", playerInfoService.getPlayerInfosByGame(game));
        return res;
    }

	@Transactional
	@GetMapping("/{gameId}/join")
    public String joinGame(@AuthenticationPrincipal UserDetails user, @PathVariable("gameId") Integer gameId, @Valid PlayerInfo joinedInfo, ModelMap model){
		Game game=gameService.getGameById(gameId);
		gameService.joinGame(game);
		Player player=playerService.getPlayerByUsername(user.getUsername());
		playerInfoService.savePlayerInfo(joinedInfo, game, player);
		model.put("game", game);
        model.put("playerInfos", playerInfoService.getPlayerInfosByGame(game));
        return "redirect:/games/" + gameId.toString() + "/lobby";
    }

	@Transactional
	@GetMapping("/{gameId}/spectate")
    public String spectateGame(@AuthenticationPrincipal UserDetails user, @PathVariable("gameId") Integer gameId, @Valid PlayerInfo spectatorInfo, ModelMap model){
		Game game=gameService.getGameById(gameId);
		Player player=playerService.getPlayerByUsername(user.getUsername());
		playerInfoService.saveSpectatorInfo(spectatorInfo, game, player);
		model.put("game", game);
        model.put("playerInfos", playerInfoService.getPlayerInfosByGame(game));
        return "redirect:/games/" + gameId.toString() + "/lobby";
    }

	@Transactional
    @GetMapping("/{gameId}")
    public ModelAndView showGame(@PathVariable("gameId") Integer gameId, @AuthenticationPrincipal UserDetails user, HttpServletResponse response){
        response.addHeader("Refresh", "2"); //cambiar el valor por el numero de segundos que se tarda en refrescar la pagina
		ModelAndView res=new ModelAndView(GAME);
        Game game=gameService.getGameById(gameId);
        SuffragiumCard suffragiumCard = suffragiumCardService.createSuffragiumCardIfNeeded(game);
		Game gameStarted = gameService.startGame(game, suffragiumCard);
		Player currentPlayer = playerService.getPlayerByUsername(user.getUsername());
		Round currentRound = roundService.getRoundByGame(game);
		Turn currentTurn = turnService.getTurnByRound(currentRound);
		Stage currentStage = stageService.getStageByTurn(currentTurn);
    	deckService.assingDecksIfNeeded(game);

		res.addObject("stage", currentStage);
		res.addObject("turn", currentTurn);
		res.addObject("round", currentRound);
		res.addObject("currentPlayer", currentPlayer);
        res.addObject("game", gameStarted);
        res.addObject("playerInfos", playerInfoService.getPlayerInfosByGame(game));
		res.addObject("suffragiumCard", suffragiumCardService.getSuffragiumCardByGame(gameId));
        return res;
    }

	@GetMapping("/{gameId}/updateSuffragium/{voteType}")
	public String updateSuffragiumCard(@PathVariable("gameId") Integer gameId, @PathVariable("voteType") VCType voteType) {
		Game actualGame = gameService.getGameById(gameId);
		Integer numLoyal = 0;
		Integer numTraitor = 0;
		if (voteType == VCType.GREEN) {
			numLoyal ++;
		}
		if (voteType == VCType.RED) {
			numTraitor ++;
		}
		suffragiumCardService.updateVotes(actualGame.getSuffragiumCard(), numLoyal, numTraitor);
		stageService.changeStage(stageService
		.getStageByTurn(turnService
		.getTurnByRound(roundService
		.getRoundByGame(actualGame))), CurrentStage.VETO);
		return "redirect:/games/" + gameId.toString();

	}

	@Transactional
    @GetMapping("/{gameId}/edit/{factionType}")
    public String selectFaction (@PathVariable("gameId") Integer gameId, @PathVariable("factionType") String factionType, @AuthenticationPrincipal UserDetails user){
        
        Player player = playerService.getPlayerByUsername(user.getUsername()); //cojo al player que esta loggeado (es el que esta eligiendo su faccion)
        Deck deck = deckService.getPlayerGameDeck(player.getId(), gameId); //cojo el mazo de este 
        List<FactionCard> chosenFaction = new ArrayList<>();
		Game game = gameService.getGameById(gameId);
		Round round = roundService.getRoundByGame(game);
		Turn turn = turnService.getTurnByRound(round);
		Stage stage = stageService.getStageByTurn(turn);

        chosenFaction.add(factionCardService.getByFaction(FCType.valueOf(factionType)));
        deckService.updateFactionDeck(deck, chosenFaction);  
		turnService.newTurn(turn);
		stageService.changeStage(stage, CurrentStage.VOTING);
        return "redirect:/games/" + gameId.toString();
    }
}
