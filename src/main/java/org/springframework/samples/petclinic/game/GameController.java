package org.springframework.samples.petclinic.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.samples.petclinic.deck.Deck;
import org.springframework.samples.petclinic.deck.DeckService;
import org.springframework.samples.petclinic.deck.VoteCard;
import org.springframework.samples.petclinic.deck.VoteCardService;
import org.springframework.samples.petclinic.deck.FactionCard.FCType;
import org.springframework.samples.petclinic.deck.VoteCard.VCType;
import org.springframework.samples.petclinic.enums.CurrentRound;
import org.springframework.samples.petclinic.enums.CurrentStage;
import org.springframework.samples.petclinic.enums.Faction;
import org.springframework.samples.petclinic.enums.RoleCard;
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
import org.springframework.web.bind.annotation.ResponseStatus;
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
	private static final String ROLE_DESIGNATION = "games/rolesDesignation";

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

    @GetMapping(value = "/playerHistory/find")
	public String gamesHistoryByPlayerForm(Map<String, Object> model) {
		model.put("game", new Game());
		return FIND_GAMES_PLAYER_HISTORY;
	}

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

    @GetMapping(value = "/inProcess/find")
	public String gamesInProcessForm(Map<String, Object> model) {
		model.put("game", new Game());
		return FIND_GAMES_IN_PROCESS;
	}

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

    @GetMapping(value = "/starting/find")
	public String gamesStartingForm(Map<String, Object> model) {
		model.put("game", new Game());
		return FIND_GAMES_STARTING;
	}

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
			return CREATE_GAME;
		} else {
			Turn turn = new Turn();
			turnService.save(turn);

			gameService.saveGame(game, turn);

			Player creator = playerService.getPlayerByUsername(user.getUsername());
			playerInfoService.saveCreatorInfo(creatorInfo, game, creator);
			
			model.put("game", game);
        	model.put("playerInfos", playerInfoService.getPlayerInfosByGame(game));
        	return "redirect:/games/" + game.getId().toString() + "/lobby";
		}
	}

    @GetMapping("/{gameId}/lobby")
    public ModelAndView showLobby(@PathVariable("gameId") Integer gameId, HttpServletResponse response){
		response.addHeader("Refresh", "2");
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
		model.put("game", game);
        model.put("playerInfos", playerInfoService.getPlayerInfosByGame(game));
        return "redirect:/games/" + gameId.toString() + "/lobby";
    }

	@GetMapping("/{gameId}/spectate")
    public String spectateGame(@AuthenticationPrincipal UserDetails user, @PathVariable("gameId") Integer gameId, @Valid PlayerInfo spectatorInfo, ModelMap model){
		Game game=gameService.getGameById(gameId);
		Player player=playerService.getPlayerByUsername(user.getUsername());
		playerInfoService.saveSpectatorInfo(spectatorInfo, game, player);
		model.put("game", game);
        model.put("playerInfos", playerInfoService.getPlayerInfosByGame(game));
        return "redirect:/games/" + gameId.toString() + "/lobby";
    }
 
    @GetMapping("/{gameId}")
    public ModelAndView showGame(@PathVariable("gameId") Integer gameId, @AuthenticationPrincipal UserDetails user, HttpServletResponse response){
        response.addHeader("Refresh", "2"); //cambiar el valor por el numero de segundos que se tarda en refrescar la pagina
		ModelAndView res=new ModelAndView(GAME);
        Game game=gameService.getGameById(gameId);
        SuffragiumCard suffragiumCard = suffragiumCardService.createSuffragiumCardIfNeeded(game);
		Game gameStarted = gameService.startGameIfNeeded(game, suffragiumCard);
		Player currentPlayer = playerService.getPlayerByUsername(user.getUsername());
		Turn currentTurn = gameStarted.getTurn();
    	deckService.assingDecksIfNeeded(game);

		Integer roleCardNumber = gameService.gameRoleCardNumber(game);
//cambiar a finished

		if(!playerInfoService.isSpectator(currentPlayer, gameStarted)){
			Deck playerDeck = deckService.getDeckByPlayerAndGame(currentPlayer, game);
			res.addObject("playerDeck", playerDeck);
		}

		if (game.getState() == State.FINISHED) {
			gameService.winnerFaction(game);
		}

		res.addObject("roleCardNumber", roleCardNumber);
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

	@GetMapping("/{gameId}/forcedVoteChange/{playerId}")
	public String pretorSelection(@PathVariable("gameId") Integer gameId,@PathVariable("playerId") Integer playerId){
		Game actualGame = gameService.getGameById(gameId);
		Player voter = playerService.getPlayerById(playerId);
		
		voteCardService.forcedVoteChange(actualGame, voter);
		System.out.println(deckService.getDeckByPlayerAndGame(voter, actualGame) + "esto");

		return "redirect:/games/" + gameId.toString();

	}

	@GetMapping("/{gameId}/pretorSelection/{voteType}/{changedVoteType}")
	public String pretorChange(@PathVariable("gameId") Integer gameId, @PathVariable("voteType") VCType voteType, 
				@PathVariable("changedVoteType") VCType changedVoteType) {
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
		suffragiumCardService.updateVotes(currentGame.getSuffragiumCard(), currentTurn, gameId);

		if (currentTurn.getCurrentTurn() == 1 && currentGame.getRound() == CurrentRound.FIRST ) {
			deckService.deckRotation(currentGame);
			gameService.changeStage(currentGame, CurrentStage.VOTING);
		}

		if (currentTurn.getCurrentTurn() != 1 && currentGame.getRound() == CurrentRound.SECOND) {
			//si es cualquier turno de la segunda ronda distinto a 1, solo se rota el consul y se pasa a votacion
			deckService.clearEdilVoteCards(currentGame);
			deckService.consulRotation(currentGame);
			gameService.changeStage(currentGame, CurrentStage.VOTING);

		}
		else {
			gameService.changeStage(currentGame, CurrentStage.END_OF_TURN);
		}
		//si se hace change stage a voting y la partida esta en round 2 el estado deberia pasar a finished y ver fin de partida en el redirect a showgames
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
		if (currentGame.getStage() == CurrentStage.SCORING) {
			updateSuffragiumCard(gameId); //como se cambia el stage arriba, si se cambiase a scoring pasamos diractamente a actualizar el sufragium
		}
		return "redirect:/games/" + gameId.toString();
	}

    @GetMapping("/{gameId}/edit/{factionType}")
    public String selectFaction (@PathVariable("gameId") Integer gameId, @PathVariable("factionType") FCType factionType, @AuthenticationPrincipal UserDetails user){
        Game game = gameService.getGameById(gameId);
		Player player = playerService.getPlayerByUsername(user.getUsername()); //cojo al player que esta loggeado (es el que esta eligiendo su faccion)
        Deck deck = deckService.getDeckByPlayerAndGame(player, game); //cojo el mazo de este 

        deckService.updateFactionDeck(deck, factionType);
		gameService.changeStage(game, CurrentStage.VOTING);

		if (game.getRound() == CurrentRound.FIRST) { //despues de elegir faccion si es primera ronda, pasa a votacion y rotan mazos
			deckService.deckRotation(game);
		}
		else { //si no es primera ronda es que es primer turno de la segunda ronda (no hay eleccion de faccion fuera de esto)
			deckService.clearEdilVoteCards(game); //borro los votos de los ediles
			deckService.consulRotation(game); //rota unicamente la carta de consul
			
		}
		
        return "redirect:/games/" + gameId.toString();
    }

	@GetMapping("/{gameId}/rolesDesignation")
    public ModelAndView rolesDesignation(@PathVariable("gameId") Integer gameId) {
		ModelAndView res = new ModelAndView(ROLE_DESIGNATION);
		List<Player> pretorCandidates = deckService.pretorCandidates(gameService.getGameById(gameId));
		List<Player> edil1Candidates = deckService.edil1Candidates(gameService.getGameById(gameId));
		List<Player> edil2Candidates = deckService.edil2Candidates(gameService.getGameById(gameId));
		Game currentGame = gameService.getGameById(gameId);

		deckService.clearDecks(currentGame);

		res.addObject("currentGame", currentGame);
		res.addObject("pretorCandidates", pretorCandidates);
		res.addObject("edil1Candidates", edil1Candidates);
		res.addObject("edil2Candidates", edil2Candidates);
		return res;
	}

	@GetMapping("/{gameId}/rolesDesignation/{pretorId}/{edil1Id}/{edil2Id}")
    public String finalRolesDesignation(@PathVariable("gameId") Integer gameId, @PathVariable("pretorId") Integer pretorId,
											@PathVariable("edil1Id") Integer edil1Id, @PathVariable("edil2Id") Integer edil2Id) {
		
		Game actualGame = gameService.getGameById(gameId);

		deckService.rolesDesignationSecondRound(actualGame, pretorId, edil1Id, edil2Id);


		return "redirect:/games/" + gameId;
		
	}	


}
