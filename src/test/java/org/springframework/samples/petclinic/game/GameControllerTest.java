package org.springframework.samples.petclinic.game;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.achievements.Achievement;
import org.springframework.samples.petclinic.achievements.AchievementRepository;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.deck.DeckRepository;
import org.springframework.samples.petclinic.deck.DeckService;
import org.springframework.samples.petclinic.deck.FactionCardRepository;
import org.springframework.samples.petclinic.deck.VoteCardRepository;
import org.springframework.samples.petclinic.deck.VoteCardService;
import org.springframework.samples.petclinic.invitation.InvitationRepository;
import org.springframework.samples.petclinic.owner.OwnerRepository;
import org.springframework.samples.petclinic.pet.PetRepository;
import org.springframework.samples.petclinic.pet.VisitRepository;
import org.springframework.samples.petclinic.player.PlayerRepository;
import org.springframework.samples.petclinic.player.PlayerService;
import org.springframework.samples.petclinic.playerInfo.PlayerInfo;
import org.springframework.samples.petclinic.playerInfo.PlayerInfoRepository;
import org.springframework.samples.petclinic.playerInfo.PlayerInfoService;
import org.springframework.samples.petclinic.progress.ProgressRepository;
import org.springframework.samples.petclinic.suffragiumCard.SuffragiumCardRepository;
import org.springframework.samples.petclinic.suffragiumCard.SuffragiumCardService;
import org.springframework.samples.petclinic.turn.TurnRepository;
import org.springframework.samples.petclinic.turn.TurnService;
import org.springframework.samples.petclinic.user.AuthoritiesRepository;
import org.springframework.samples.petclinic.user.UserRepository;
import org.springframework.samples.petclinic.vet.VetRepository;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@WebMvcTest(controllers = GameController.class, 
            excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, 
            classes = WebSecurityConfigurer.class), 
            excludeAutoConfiguration = SecurityConfiguration.class)
public class GameControllerTest {

    @Autowired
    private MockMvc mockMvc;
 
    @MockBean
    private GameService gameService;
    
    @WithMockUser
    @Test
    public void testGamesHistoryForm() throws Exception {
        mockMvc.perform(get("/games/playerHistory/find"))
        .andExpect(status().isOk())
        .andExpect(view().name("/games/findGamesPlayerHistory"))
        .andExpect(model().attributeExists("game"));
    }

    /* 
    The following MockBeans are necessary to make controller tests work correctly, since we're using the annotation
    "@EnableJpaRepositories(repositoryFactoryBeanClass = EnversRevisionRepositoryFactoryBean.class)" in "PetclinicApplication.java".
    This anotation is required to access audit information about players, but forces us to create the MockBeans.
    */
    
    @MockBean
    private PlayerService playerService;

    @MockBean
    private PlayerInfoService playerInfoService;

    @MockBean
	private SuffragiumCardService suffragiumCardService;

	@MockBean
	private DeckService deckService;

	@MockBean
	private TurnService turnService;

	@MockBean
	private VoteCardService voteCardService;

    @MockBean
    private GameRepository gameRepository;

    @MockBean
    private PlayerRepository playerRepository;

    @MockBean
    private PlayerInfoRepository playerInfoRepository;

    @MockBean
	private SuffragiumCardRepository suffragiumCardRepository;

	@MockBean
	private DeckRepository deckRepository;

	@MockBean
	private TurnRepository turnRepository;

	@MockBean
	private VoteCardRepository voteCardRepository;

    @MockBean
    private InvitationRepository invitationRepository;

    @MockBean
    private AchievementRepository achievementRepository;

    @MockBean
    private ProgressRepository progressRepository;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private AuthoritiesRepository authoritiesRepository;

    @MockBean
    private FactionCardRepository factionCardRepository;

    @MockBean
    private PetRepository petRepository;

    @MockBean
    private VetRepository vetRepository;

    @MockBean
    private OwnerRepository ownerRepository;

    @MockBean
    private VisitRepository visitRepository;
}
