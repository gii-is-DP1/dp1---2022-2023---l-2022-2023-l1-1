package org.springframework.samples.petclinic.game;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.deck.Deck;
import org.springframework.samples.petclinic.deck.DeckService;
import org.springframework.samples.petclinic.deck.FactionCardService;
import org.springframework.samples.petclinic.deck.VoteCard;
import org.springframework.samples.petclinic.deck.VoteCardService;
import org.springframework.samples.petclinic.deck.FactionCard.FCType;
import org.springframework.samples.petclinic.deck.VoteCard.VCType;
import org.springframework.samples.petclinic.enums.CurrentStage;
import org.springframework.samples.petclinic.enums.State;
import org.springframework.samples.petclinic.player.Player;
import org.springframework.samples.petclinic.player.PlayerService;
import org.springframework.samples.petclinic.playerInfo.PlayerInfo;
import org.springframework.samples.petclinic.playerInfo.PlayerInfoService;
import org.springframework.samples.petclinic.suffragiumCard.SuffragiumCard;
import org.springframework.samples.petclinic.suffragiumCard.SuffragiumCardService;
import org.springframework.samples.petclinic.turn.Turn;
import org.springframework.samples.petclinic.turn.TurnService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
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
	private static final String PRETOR_SELECTION = "games/pretorCardSelection";

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
	private TurnService turnService;

	@Autowired
	private VoteCardService voteCardService;

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
	public ModelAndView createGame(@AuthenticationPrincipal UserDetails user, @Valid PlayerInfo creatorInfo, 
	@Valid Game game, BindingResult br) {
		ModelAndView res = null;
		if(br.hasErrors()) {
			return new ModelAndView(CREATE_GAME, br.getModel());
		} else {
			Turn turn = new Turn();
			turnService.save(turn);

			Game newGame = gameService.saveGame(game, turn);

			Player creator = playerService.getPlayerByUsername(user.getUsername());

			playerInfoService.saveCreatorInfo(creatorInfo, game, creator);
			
			res = showLobby(newGame.getId());
			res.addObject("message", "Game successfully created!");
		}
		return res;
	}

	@Transactional
    @GetMapping("/{gameId}/lobby")
    public ModelAndView showLobby(@PathVariable("gameId") Integer gameId){
        ModelAndView res=new ModelAndView(GAME_LOBBY);
        Game game=gameService.getGameById(gameId);
        res.addObject("game", game);
        res.addObject("playerInfos", playerInfoService.getPlayerInfosByGame(game));
        return res;
    }

	@Transactional
    @GetMapping("/{gameId}")
    public ModelAndView showGame(@PathVariable("gameId") Integer gameId, @AuthenticationPrincipal UserDetails user, HttpServletResponse response){
        response.addHeader("Refresh", "5"); //cambiar el valor por el numero de segundos que se tarda en refrescar la pagina
		ModelAndView res=new ModelAndView(GAME);
        Game game=gameService.getGameById(gameId);
        SuffragiumCard suffragiumCard = suffragiumCardService.createSuffragiumCardIfNeeded(game);
		Game gameStarted = gameService.startGame(game, suffragiumCard);
		Player currentPlayer = playerService.getPlayerByUsername(user.getUsername());
		Turn currentTurn = turnService.getTurnByGame(gameStarted);
    	deckService.assingDecksIfNeeded(game);
		Deck playerDeck = deckService.getPlayerGameDeck(currentPlayer.getId(), gameId);

		res.addObject("playerDeck", playerDeck);
		res.addObject("turn", currentTurn);
		res.addObject("currentPlayer", currentPlayer);
        res.addObject("game", gameStarted);
        res.addObject("playerInfos", playerInfoService.getPlayerInfosByGame(game));
		res.addObject("suffragiumCard", suffragiumCardService.getSuffragiumCardByGame(gameId));
        return res;
    }

	@GetMapping("/{gameId}/pretorSelection/{voteType}")
	public ModelAndView pretorSelection(@PathVariable("gameId") Integer gameId, @PathVariable("voteType") VCType voteType) {
		ModelAndView res = new ModelAndView(PRETOR_SELECTION);
		VoteCard selectedCard = voteCardService.getById(voteType);
		List <VoteCard> changeOptions = voteCardService.getChangeOptions(gameService.getGameById(gameId), selectedCard);
		

		res.addObject("game", gameService.getGameById(gameId));
		res.addObject("selectedCard", selectedCard);
		res.addObject("changeOptions", changeOptions);
		
		return res;
	}

	@GetMapping("/{gameId}/pretorSelection/{voteType}/{changedVoteType}")
	public String pretorChange(@PathVariable("gameId") Integer gameId, @PathVariable("voteType") VCType voteType, @PathVariable("changedVoteType") VCType changedVoteType) {
		Game game = gameService.getGameById(gameId);
		turnService.pretorVoteChange(voteType, changedVoteType, gameService.getGameById(gameId));
		gameService.changeStage(game, CurrentStage.SCORING);
		updateSuffragiumCard(gameId);
		return "redirect:/games/" + gameId.toString();
		
	}

	@GetMapping("/{gameId}/updateSuffragium")
	public String updateSuffragiumCard(@PathVariable("gameId") Integer gameId) {
		Game currentGame = gameService.getGameById(gameId);
		Turn currentTurn = turnService.getTurnByGame(currentGame);
		suffragiumCardService.updateVotes(currentGame.getSuffragiumCard(), currentTurn);
		gameService.changeStage(currentGame, CurrentStage.END_OF_TURN);
		return "redirect:/games/" + gameId.toString();
	}

	@GetMapping("/{gameId}/updateVotes/{voteType}")
	public String updateTurnVotes(@PathVariable("gameId") Integer gameId, @PathVariable("voteType") VCType voteType, @AuthenticationPrincipal UserDetails user) {
		Game currentGame = gameService.getGameById(gameId);
		Turn currentTurn = turnService.getTurnByGame(currentGame);
		Player currentPlayer = playerService.getPlayerByUsername(user.getUsername());
		Deck deck = deckService.getPlayerGameDeck(currentPlayer.getId(), gameId);

		turnService.updateTurnVotes(currentTurn, voteType);
		gameService.changeStageIfVotesCompleted(currentGame);
		deckService.updateVotesDeck(deck, voteType);
		return "redirect:/games/" + gameId.toString();
	}

	@Transactional
    @GetMapping("/{gameId}/edit/{factionType}")
    public String selectFaction (@PathVariable("gameId") Integer gameId, @PathVariable("factionType") FCType factionType, @AuthenticationPrincipal UserDetails user){
        Player player = playerService.getPlayerByUsername(user.getUsername()); //cojo al player que esta loggeado (es el que esta eligiendo su faccion)
        Deck deck = deckService.getPlayerGameDeck(player.getId(), gameId); //cojo el mazo de este 
		Game game = gameService.getGameById(gameId);

        deckService.updateFactionDeck(deck, factionType);  
		gameService.changeTurnAndRound(game);
		gameService.changeStage(game, CurrentStage.VOTING);
        return "redirect:/games/" + gameId.toString();
    }

	
}
