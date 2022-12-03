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
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.player.Player;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

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
/* 
    @WithMockUser
    @Test
    public void testShowInvitationsByPlayer() throws Exception {
        mockMvc.perform(get("/invitations"))
        .andExpect(status().isOk())
        .andExpect(view().name("invitations/invitationsList"))
        .andExpect(model().attributeExists("invitations"));
    }*/
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
