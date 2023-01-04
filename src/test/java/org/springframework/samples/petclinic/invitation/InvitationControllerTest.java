package org.springframework.samples.petclinic.invitation;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.achievements.AchievementRepository;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.game.GameRepository;
import org.springframework.samples.petclinic.game.GameService;
import org.springframework.samples.petclinic.player.Player;
import org.springframework.samples.petclinic.player.PlayerService;
import org.springframework.samples.petclinic.playerInfo.PlayerInfoRepository;
import org.springframework.samples.petclinic.playerInfo.PlayerInfoService;
import org.springframework.samples.petclinic.progress.ProgressRepository;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.samples.petclinic.deck.DeckRepository;
import org.springframework.samples.petclinic.deck.FactionCardRepository;
import org.springframework.samples.petclinic.deck.VoteCardRepository;
import org.springframework.samples.petclinic.deck.VoteCardService;
import org.springframework.samples.petclinic.invitation.InvitationRepository;
import org.springframework.samples.petclinic.owner.OwnerRepository;
import org.springframework.samples.petclinic.pet.PetRepository;
import org.springframework.samples.petclinic.pet.VisitRepository;
import org.springframework.samples.petclinic.player.PlayerRepository;
import org.springframework.samples.petclinic.user.AuthoritiesRepository;
import org.springframework.samples.petclinic.user.UserRepository;
import org.springframework.samples.petclinic.vet.VetRepository;
import org.springframework.samples.petclinic.suffragiumCard.SuffragiumCardRepository;
import org.springframework.samples.petclinic.turn.TurnRepository;

@WebMvcTest(controllers = InvitationController.class, 
            excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, 
            classes = WebSecurityConfigurer.class), 
            excludeAutoConfiguration = SecurityConfiguration.class)
public class InvitationControllerTest {

    private static final Integer TEST_PLAYER_ID = 1;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InvitationService invitationService;

    @MockBean
    private PlayerService playerService;

    @MockBean
    private GameService gameService;

    @MockBean
    private PlayerInfoService playerInfoService;

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
 
    @WithMockUser
    @Test
    public void testShowInvitationsByPlayer() throws Exception {
        mockMvc.perform(get("/invitations"))
        .andExpect(status().isOk())
        .andExpect(view().name("invitations/invitationsList"))
        .andExpect(model().attributeExists("invitations"));
    }
/* 
    @WithMockUser
    @Test
    public void testSendInvitation() throws Exception {
        mockMvc.perform(get("/invitations/send"))
        .andExpect(status().isOk())
        .andExpect(view().name("invitations/sendInvitation"))
        .andExpect(model().attributeExists("players"))
        .andExpect(model().attributeExists("invitation"));
    }*/
/* 
    @WithMockUser
    @Test
    public void testSaveInvitation() throws Exception {
        mockMvc.perform(post("/invitations/send", null)
        .with(csrf())
        .param("recipient", "2")
        .param("message", "Testing invitations"))
        .andExpect(status().isOk())
        .andExpect(view().name("invitations/invitationsList"))
        .andExpect(model().attributeExists("invitations"));

        verify(invitationService).saveInvitation(any(Invitation.class), any(Player.class));    
    }*/
}
