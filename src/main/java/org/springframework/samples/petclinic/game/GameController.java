package org.springframework.samples.petclinic.game;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.deck.Deck;
import org.springframework.samples.petclinic.deck.DeckService;
import org.springframework.samples.petclinic.deck.FactionCard;
import org.springframework.samples.petclinic.deck.FactionCardService;
import org.springframework.samples.petclinic.deck.FactionCard.FCType;
import org.springframework.samples.petclinic.deck.VoteCard.VCType;
import org.springframework.samples.petclinic.enums.State;
import org.springframework.samples.petclinic.player.Player;
import org.springframework.samples.petclinic.player.PlayerService;
import org.springframework.samples.petclinic.playerInfo.PlayerInfo;
import org.springframework.samples.petclinic.playerInfo.PlayerInfoService;
import org.springframework.samples.petclinic.suffragiumCard.SuffragiumCard;
import org.springframework.samples.petclinic.suffragiumCard.SuffragiumCardService;
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
    public ModelAndView createGameForm() {
        ModelAndView res = new ModelAndView(CREATE_GAME);
        Game game = new Game();   
		SuffragiumCard card = new SuffragiumCard(); 
		PlayerInfo creatorInfo = new PlayerInfo();     
        res.addObject("game", game);
		res.addObject("suffragiumCard", card); 
		res.addObject("creatorInfo", creatorInfo);                                 
        return res;
    }

	@Transactional
	@PostMapping("/create")
	public ModelAndView createGame(@AuthenticationPrincipal UserDetails user, @Valid PlayerInfo creatorInfo, 
	@Valid SuffragiumCard card, @Valid Game game, BindingResult br) {
		ModelAndView res = null;
		if(br.hasErrors()) {
			return new ModelAndView(CREATE_GAME, br.getModel());
		} else {
			SuffragiumCard newCard = suffragiumCardService.saveSuffragiumCard(card);
			Game newGame = gameService.saveGame(game, newCard);
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
    public ModelAndView showGame(@PathVariable("gameId") Integer gameId, @AuthenticationPrincipal UserDetails user){
        ModelAndView res=new ModelAndView(GAME);
        Game game=gameService.getGameById(gameId);
		Player actualPlayer = playerService.getPlayerByUsername(user.getUsername());
		res.addObject("actualPlayer", actualPlayer);
        res.addObject("game", game);
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
		return "redirect:/games/" + gameId.toString();

	}

	@Transactional
    @GetMapping("/{gameId}/edit/{factionType}")
    public String selectFaction (@PathVariable("gameId") Integer gameId, @PathVariable("factionType") String factionType, @AuthenticationPrincipal UserDetails user){
        
        Player player = playerService.getPlayerByUsername(user.getUsername()); //cojo al player que esta loggeado (es el que esta eligiendo su faccion)
        Deck deck = deckService.getPlayerGameDeck(player.getId(), gameId); //cojo el mazo de este 
        List<FactionCard> chosenFaction = new ArrayList<>();
        chosenFaction.add(factionCardService.getByFaction(FCType.valueOf(factionType)));
        deckService.updateFactionDeck(deck, chosenFaction);  
        System.out.println("esto" + deckService.getPlayerGameDeck(player.getId(), gameId).getId());     
        return "redirect:/games/" + gameId.toString();
    }
}
