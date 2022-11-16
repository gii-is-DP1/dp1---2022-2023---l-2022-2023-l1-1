package org.springframework.samples.petclinic.achievements;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.samples.petclinic.player.Player;
import org.springframework.samples.petclinic.player.PlayerService;
import org.springframework.samples.petclinic.progress.Progress;
import org.springframework.samples.petclinic.progress.ProgressService;
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

@Controller
@RequestMapping("/achievements")
public class AchievementController {

    private final String ACHIEVEMENTS_LISTING_VIEW="/achievements/achievementsListing";
    private final String ACHIEVEMENTS_FORM="/achievements/createOrUpdateAchievementForm";
    private final String USER_ACHIEVEMENTS_VIEW="/achievements/playerAchievements";
    private AchievementService achievementService;
    private ProgressService progressService;
    private PlayerService playerService;

    @Autowired
    public AchievementController(AchievementService achievementService, ProgressService progressService, PlayerService playerService){
        this.achievementService = achievementService;
        this.progressService = progressService;
        this.playerService = playerService;
    }
    
    @GetMapping
    public ModelAndView showAchievements(){
        ModelAndView result=new ModelAndView(ACHIEVEMENTS_LISTING_VIEW);
        result.addObject("achievements", achievementService.getAchievements());
        return result;
    }

    @GetMapping("/player")
    public ModelAndView showUserAchievements(@AuthenticationPrincipal UserDetails user) {
        ModelAndView result=new ModelAndView(USER_ACHIEVEMENTS_VIEW);
        Player pl = playerService.getPlayerByUsername(user.getUsername());
        result.addObject("progress", progressService.getUserProgress(pl));
        return result;
    }

    @GetMapping("/{id}/delete")
    public ModelAndView deleteAchievement(@PathVariable int id, ModelMap model){
        String message;
        try{
            achievementService.deleteAchievementById(id);  
            message = "Removed successfully";      
        } catch(EmptyResultDataAccessException e) {
            message = "Achievement " + id + " does not exist";
        }
        model.put("message", message);
        model.put("messagetype", "info");
        return showAchievements();

    }

    @GetMapping("/{id}/edit")
    public ModelAndView editAchievement(@PathVariable int id, ModelMap model){
        Achievement achievement=achievementService.getById(id);        
        ModelAndView result=new ModelAndView(ACHIEVEMENTS_FORM);
            result.addObject("achievement", achievement);
            return result;
    }


    @PostMapping("/{id}/edit")
    public ModelAndView saveAchievement(@PathVariable int id, @Valid Achievement achievement, BindingResult br){
        ModelAndView result = showAchievements();
        if (br.hasErrors()) {
            return new ModelAndView(ACHIEVEMENTS_FORM, br.getModel());
        }
        Achievement achievementToBeUpdated=achievementService.getById(id);
        BeanUtils.copyProperties(achievement,achievementToBeUpdated,"id");
        achievementService.saveAchievement(achievementToBeUpdated);
        result.addObject("message", "Achievement edited succesfully!");
        return result;
    }

    @GetMapping("/create")
    public ModelAndView createAchievement(){
        Achievement achievement=new Achievement();
        ModelAndView result=new ModelAndView(ACHIEVEMENTS_FORM);
        result.addObject("achievement", achievement);
        return result;
    }

    @PostMapping("/create")
    public ModelAndView saveNewAchievement(@Valid Achievement achievement, BindingResult br){
        ModelAndView result = null;
       if (br.hasErrors()) {
        return new ModelAndView(ACHIEVEMENTS_FORM, br.getModel());
       } else {
        achievementService.saveAchievement(achievement);
        for (Progress progress : progressService.addNewAchievement(achievement)) {
            progressService.saveProgress(progress);
        }
        result = showAchievements();
        result.addObject("message", "Achievement saved succesfully!");
       }

       return result;
    }
}