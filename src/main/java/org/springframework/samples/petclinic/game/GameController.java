package org.springframework.samples.petclinic.game;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.deck.Deck;
import org.springframework.samples.petclinic.deck.DeckService;
import org.springframework.samples.petclinic.deck.VoteCard;
import org.springframework.samples.petclinic.deck.VoteCardService;
import org.springframework.samples.petclinic.deck.FactionCard.FCType;
import org.springframework.samples.petclinic.deck.VoteCard.VCType;
import org.springframework.samples.petclinic.enums.CurrentRound;
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
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/games")
public class GameController {

	private static final String GAMES_STARTING_LIST = "/games/gamesStartingList";
	private static final String GAMES_IN_PROCESS_LIST = "/games/gamesInProcessList";
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

    @GetMapping(value = "/history/find")
	public String gamesHistoryForm(Map<String, Object> model) {
		model.put("game", new Game());
		return FIND_GAMES_HISTORY;
	}

    @GetMapping(value = "/history")
	public ModelAndView processGamesHistoryForm(Game game, BindingResult result) {

		// allow parameterless GET request for /games to return all records
		if (game.getName() == null) {
			game.setName(""); // empty string signifies broadest possible search
			log.warn("Null string input changed to empty string");
		}

		// find games by name
		List<Game> publicGames = this.gameService.getPublicGamesByNameAndState(game.getName(), State.FINISHED);
		List<Game> privateGames = this.gameService.getPrivateGamesByNameAndState(game.getName(), State.FINISHED);
		if (publicGames.isEmpty() && privateGames.isEmpty()) {
			log.warn("No games found");
			result.rejectValue("name", "notFound", "not found");
			return new ModelAndView(FIND_GAMES_HISTORY);
		}
		else {
			// games found
			ModelAndView res = new ModelAndView(GAMES_FINISHED_LIST);
			res.addObject("returnButton", "/games/history/find");
            res.addObject("publicGames", publicGames); 
			res.addObject("privateGames", privateGames); 
			return res;
		}
	}

    @GetMapping(value = "/playerHistory/find")
	public String gamesHistoryByPlayerForm(Map<String, Object> model) {
		model.put("game", new Game());
		return FIND_GAMES_PLAYER_HISTORY;
	}

    @GetMapping(value = "/playerHistory")
	public ModelAndView processGamesHistoryByPlayerForm(@AuthenticationPrincipal UserDetails user, Game game, BindingResult result) {
		if (game.getName() == null) {
			game.setName("");
			log.warn("Null string input changed to empty string");
		}

		Player player = playerService.getPlayerByUsername(user.getUsername());
		List<Game> publicGames = gameService.getPlayerGamesHistory(game.getName(), player, true);
		List<Game> privateGames = gameService.getPlayerGamesHistory(game.getName(), player, false);
		if (publicGames.isEmpty() && privateGames.isEmpty()) {
			log.warn("No games found");
			result.rejectValue("name", "notFound", "not found");
			return new ModelAndView(FIND_GAMES_PLAYER_HISTORY);
		}
		else {
			ModelAndView res = new ModelAndView(GAMES_FINISHED_LIST);
			res.addObject("returnButton", "/games/playerHistory/find");
            res.addObject("publicGames", publicGames); 
			res.addObject("privateGames", privateGames);
			return res;
		}
	}

    @GetMapping(value = "/inProcess/find")
	public String gamesInProcessForm(Map<String, Object> model) {
		model.put("game", new Game());
		return FIND_GAMES_IN_PROCESS;
	}

    @GetMapping(value = "/inProcess")
	public ModelAndView processGamesInProcessForm(Game game, BindingResult result) {
		if (game.getName() == null) {
			game.setName("");
			log.warn("Null string input changed to empty string");
		}

		List<Game> publicGames = this.gameService.getPublicGamesByNameAndState(game.getName(), State.IN_PROCESS);
		List<Game> privateGames = this.gameService.getPrivateGamesByNameAndState(game.getName(), State.IN_PROCESS);
		if (publicGames.isEmpty() && privateGames.isEmpty()) {
			log.warn("No games found");
			result.rejectValue("name", "notFound", "not found");
			return new ModelAndView(FIND_GAMES_IN_PROCESS);
		}
		else {
			ModelAndView res = new ModelAndView(GAMES_IN_PROCESS_LIST);
			res.addObject("returnButton", "/games/inProcess/find");
            res.addObject("publicGames", publicGames); 
			res.addObject("privateGames", privateGames);
			return res;
		}
	}

    @GetMapping(value = "/starting/find")
	public String gamesStartingForm(Map<String, Object> model) {
		model.put("game", new Game());
		return FIND_GAMES_STARTING;
	}

    @GetMapping(value = "/starting")
	public ModelAndView processGamesStartingForm(Game game, BindingResult result, @AuthenticationPrincipal UserDetails user) {
		if (game.getName() == null) {
			game.setName("");
			log.warn("Null string input changed to empty string");
		}

		Player player = playerService.getPlayerByUsername(user.getUsername());
		List<Game> publicGames = this.gameService.getPublicGamesByNameAndState(game.getName(), State.STARTING);
		List<Game> friendsGames = this.gameService.getFriendGamesByNameAndState(game.getName(), State.STARTING, player);

		if (publicGames.isEmpty() && friendsGames.isEmpty()) {
			log.warn("No games found");
			result.rejectValue("name", "notFound", "not found");
			return new ModelAndView(FIND_GAMES_STARTING);
		}
		else {
			ModelAndView res = new ModelAndView(GAMES_STARTING_LIST);
			res.addObject("returnButton", "/games/starting/find");
            res.addObject("publicGames", publicGames); 
			res.addObject("friendsGames", friendsGames); 
			return res;
		}
	}

    @GetMapping("/create")
    public ModelAndView createGameForm() {
        ModelAndView res = new ModelAndView(CREATE_GAME);
        Game game = new Game();       
        res.addObject("game", game);                                
        return res;
    }

	@PostMapping("/create")
	public String createGame(@AuthenticationPrincipal UserDetails user, @Valid PlayerInfo creatorInfo, 
	@Valid Game game, BindingResult br, ModelMap model) {
		if(br.hasErrors()) {
			log.error("Input value error");
			return CREATE_GAME;
		} else {
			Turn turn = new Turn();
			turnService.save(turn);

			gameService.saveGame(game, turn);

			Player creator = playerService.getPlayerByUsername(user.getUsername());
			playerInfoService.saveCreatorInfo(creatorInfo, game, creator);
			log.info("Game created");

			model.put("game", game);
        	model.put("playerInfos", playerInfoService.getPlayerInfosByGame(game));
        	return "redirect:/games/" + game.getId().toString() + "/lobby";
		}
	}

    @GetMapping("/{gameId}/lobby")
    public ModelAndView showLobby(@PathVariable("gameId") Integer gameId, HttpServletResponse response){
		response.addHeader("Refresh", "3");
        ModelAndView res=new ModelAndView(GAME_LOBBY);
        Game game=gameService.getGameById(gameId);
        res.addObject("game", game);
        res.addObject("playerInfos", playerInfoService.getPlayerInfosByGame(game));
        return res;
    }

	@GetMapping("/{gameId}/join")
    public String joinGame(@AuthenticationPrincipal UserDetails user, @PathVariable("gameId") Integer gameId, @Valid PlayerInfo joinedInfo, ModelMap model){
		Game game=gameService.getGameById(gameId);
		gameService.joinGame(game);
		Player player=playerService.getPlayerByUsername(user.getUsername());
		playerInfoService.savePlayerInfo(joinedInfo, game, player);
		log.info("Player joined");
		model.put("game", game);
        model.put("playerInfos", playerInfoService.getPlayerInfosByGame(game));
        return "redirect:/games/" + gameId.toString() + "/lobby";
    }

	@GetMapping("/{gameId}/spectate")
    public String spectateGame(@AuthenticationPrincipal UserDetails user, @PathVariable("gameId") Integer gameId, @Valid PlayerInfo spectatorInfo, ModelMap model){
		Game game=gameService.getGameById(gameId);
		Player player=playerService.getPlayerByUsername(user.getUsername());
		playerInfoService.saveSpectatorInfo(spectatorInfo, game, player);
		log.info("Spectator joined");
		model.put("game", game);
        model.put("playerInfos", playerInfoService.getPlayerInfosByGame(game));
        return "redirect:/games/" + gameId.toString() + "/lobby";
    }

    @GetMapping("/{gameId}")
    public ModelAndView showGame(@PathVariable("gameId") Integer gameId, @AuthenticationPrincipal UserDetails user, HttpServletResponse response){
        response.addHeader("Refresh", "2");
		ModelAndView res=new ModelAndView(GAME);
        Game game=gameService.getGameById(gameId);
        SuffragiumCard suffragiumCard = suffragiumCardService.createSuffragiumCardIfNeeded(game);
		Game gameStarted = gameService.startGameIfNeeded(game, suffragiumCard);
		Player currentPlayer = playerService.getPlayerByUsername(user.getUsername());
		Turn currentTurn = gameStarted.getTurn();
    	deckService.assingDecksIfNeeded(game);
		if(!playerInfoService.isSpectator(currentPlayer, gameStarted)){
			Deck playerDeck = deckService.getDeckByPlayerAndGame(currentPlayer, game);
			res.addObject("playerDeck", playerDeck);
		} 
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
		Turn currentTurn = currentGame.getTurn();
		suffragiumCardService.updateVotes(currentGame.getSuffragiumCard(), currentTurn);
		if (currentTurn.getCurrentTurn() == 1 && currentGame.getRound() == CurrentRound.FIRST ) {
			deckService.deckRotation(currentGame);
			gameService.changeStage(currentGame, CurrentStage.VOTING);
		}
		/*else if (currentTurn.getCurrentTurn() > 1 && currentGame.getRound() == CurrentRound.SECOND) {
			deckService.deckRotation(currentGame);
			gameService.changeStage(currentGame, CurrentStage.VOTING);
		}*/ 
		//esto teoricamente deberia funcionar pero lo comento porque no lo he probado
		//de todas formas hay que ver como planteamos la asignacion de roles en la segunda ronda asi que esto se cambiaria cuando la signacón este
		
		else {
			gameService.changeStage(currentGame, CurrentStage.END_OF_TURN);
		}
		return "redirect:/games/" + gameId.toString();
	}

	@GetMapping("/{gameId}/updateVotes/{voteType}")
	public String updateTurnVotes(@PathVariable("gameId") Integer gameId, @PathVariable("voteType") VCType voteType, @AuthenticationPrincipal UserDetails user) {
		Game currentGame = gameService.getGameById(gameId);
		Turn currentTurn = currentGame.getTurn();
		Player currentPlayer = playerService.getPlayerByUsername(user.getUsername());
		Deck deck = deckService.getDeckByPlayerAndGame(currentPlayer, currentGame);

		turnService.updateTurnVotes(currentTurn, voteType);
		gameService.changeStageIfVotesCompleted(currentGame);
		deckService.updateVotesDeck(deck, voteType);
		return "redirect:/games/" + gameId.toString();
	}

    @GetMapping("/{gameId}/edit/{factionType}")
    public String selectFaction (@PathVariable("gameId") Integer gameId, @PathVariable("factionType") FCType factionType, @AuthenticationPrincipal UserDetails user){
        Game game = gameService.getGameById(gameId);
		Player player = playerService.getPlayerByUsername(user.getUsername()); //cojo al player que esta loggeado (es el que esta eligiendo su faccion)
        Deck deck = deckService.getDeckByPlayerAndGame(player, game); //cojo el mazo de este 

        deckService.updateFactionDeck(deck, factionType);
		gameService.changeStage(game, CurrentStage.VOTING);
		deckService.deckRotation(game);
        return "redirect:/games/" + gameId.toString();
    }

	
}
