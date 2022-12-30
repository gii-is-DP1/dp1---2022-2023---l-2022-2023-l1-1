package org.springframework.samples.petclinic.game;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.deck.DeckRepository;
import org.springframework.samples.petclinic.deck.FactionCard.FCType;
import org.springframework.samples.petclinic.enums.CurrentRound;
import org.springframework.samples.petclinic.enums.CurrentStage;
import org.springframework.samples.petclinic.enums.Faction;
import org.springframework.samples.petclinic.enums.RoleCard;
import org.springframework.samples.petclinic.enums.State;
import org.springframework.samples.petclinic.suffragiumCard.SuffragiumCard;
import org.springframework.samples.petclinic.turn.Turn;
import org.springframework.samples.petclinic.turn.TurnRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GameService {
    
    @Autowired
    private GameRepository repo;

    @Autowired
    private TurnRepository turnRepository;

    @Autowired
    private DeckRepository deckRepository;

    @Autowired
    public GameService(GameRepository repo) {
        this.repo = repo;
    }

    @Transactional(readOnly = true)
    public Game getGameById(Integer id){
        return repo.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Game> getGamesByNameAndState(String name, State s) {
        return repo.findByName(name).stream().filter(g -> g.getState() == s).collect(Collectors.toList());
    }

    private static final Integer STARTING_NUMBER_OF_PLAYERS = 1;

    @Transactional
    public Game saveGame(Game game, Turn turn) throws DataAccessException {
        game.setState(State.STARTING);
        game.setNumPlayers(STARTING_NUMBER_OF_PLAYERS);
        LocalDate date = LocalDate.now();
        game.setDate(date);
        game.setDuration(0.);
        game.setRound(CurrentRound.FIRST);
        game.setTurn(turn);
        game.setStage(CurrentStage.VOTING);
        game.setWinners(null);
        game.setSuffragiumCard(null);
        return repo.save(game);
    }

    @Transactional
    public Game startGameIfNeeded(Game game, SuffragiumCard suffragiumCard) throws DataAccessException {
        if(game.getState() == State.STARTING) {
            game.setState(State.IN_PROCESS);
            game.setSuffragiumCard(suffragiumCard);
        }
        return repo.save(game);
    }

    @Transactional
    public void joinGame(Game game) throws DataAccessException {
        game.setNumPlayers(game.getNumPlayers()+1);
        repo.save(game);
    }
    
    @Transactional
    public void changeStage(Game game, CurrentStage stage) {
        game.setStage(stage);
        if (stage == CurrentStage.VOTING) {
            changeTurnAndRound(game); //si cambiamos a voting es pq pasamos de turno
        }
        repo.save(game);
    }

    private static final Integer TOTAL_VOTES_NUMBER = 2;

    @Transactional
    public void changeStageIfVotesCompleted(Game game) {
        if(game.getTurn().getVoteCount() == TOTAL_VOTES_NUMBER) {
            game.setStage(CurrentStage.VETO);
            repo.save(game);
        }
        else if(game.getTurn().getVoteCount() > TOTAL_VOTES_NUMBER) {
            game.setStage(CurrentStage.SCORING);
            repo.save(game);
        }
    }

    
    private static final Integer NEW_TURN_INITIAL_VOTES = 0;

    @Transactional
    public void changeTurnAndRound(Game game) {
        Turn turnToChange = game.getTurn();
        turnToChange.setVotesLoyal(NEW_TURN_INITIAL_VOTES);
        turnToChange.setVotesTraitor(NEW_TURN_INITIAL_VOTES);
        turnToChange.setVotesNeutral(NEW_TURN_INITIAL_VOTES);
        turnToChange.setCurrentTurn(turnToChange.getCurrentTurn() + 1);
        if(game.getTurn().getCurrentTurn() > game.getNumPlayers()) {
            turnToChange.setCurrentTurn(1);
            turnRepository.save(turnToChange);
            if(game.getRound() == CurrentRound.FIRST){
                game.setRound(CurrentRound.SECOND);
            }
            else if (game.getRound() == CurrentRound.SECOND) {
                game.setState(State.FINISHED);
            }
        }
        turnRepository.save(turnToChange);
        repo.save(game);
    }

    @Transactional
    public Integer gameRoleCardNumber (Game game) {
        Integer res = deckRepository.findAll().stream()
            .filter(x -> x.getGame() == game).filter(y -> y.getRoleCard() != RoleCard.NO_ROL).collect(Collectors.toList()).size();
        return res;
    }

    @Transactional
    public void winnerFaction (Game game) {
        Integer loyalVotes = game.getSuffragiumCard().getLoyalsVotes();
        Integer traitorVotes = game.getSuffragiumCard().getTraitorsVotes();
        Integer voteLimit = game.getSuffragiumLimit();
        Faction winner;

        if (loyalVotes >= voteLimit || traitorVotes >= voteLimit) { //conspiracion fallida
            if (loyalVotes >= voteLimit) {
                winner = Faction.TRAITORS; //si supera leales gana traidor
            }
            else {
                winner = Faction.LOYALS; //si no, esque ha superado traidor y gana leales
            }
            //EN ESTE CASO SI NO HAY FACCION RIVAL GANARIA MERCADER A VER COMO SE PONE ESO
        }

        else { //idus de marzo
            if ((loyalVotes + 1) < traitorVotes) {
                winner =  Faction.TRAITORS;
            }

            else if ((traitorVotes + 1) < loyalVotes) {
                winner = Faction.LOYALS;
            }
            
            else {
                winner =  Faction.MERCHANTS;
            }
        }
        game.setWinners(winner);
        repo.save(game);
    }
    
}
