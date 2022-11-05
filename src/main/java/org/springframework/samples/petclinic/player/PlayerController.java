package org.springframework.samples.petclinic.player;

import org.springframework.beans.factory.annotation.Autowired;

public class PlayerController {
    private PlayerService playerService;

    //private static final String VIEWS_PLAYER_LIST = "player/feedingList";

    @Autowired
    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    /* 
    @GetMapping
    public ModelAndView showPlayerList() {
        ModelAndView mav = new ModelAndView(VIEWS_PLAYER_LIST);
        mav.addObject("player", playerService.getAll());
        return mav;
    }
    */
}
