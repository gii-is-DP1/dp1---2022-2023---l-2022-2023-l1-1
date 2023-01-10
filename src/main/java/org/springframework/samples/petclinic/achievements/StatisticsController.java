package org.springframework.samples.petclinic.achievements;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.player.PlayerService;
import org.springframework.samples.petclinic.progress.ProgressService;
import org.springframework.samples.petclinic.user.User;
import org.springframework.samples.petclinic.user.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class StatisticsController {

    private final String STATISTICS_VIEW="/statistics/statistics";
    private final String RANKING_VIEW="/statistics/ranking";

    @Autowired
    private UserService userService;
    private ProgressService progressService;
    private PlayerService playerService;
    private StatisticsService statisticsService;

    @Autowired
    public StatisticsController(StatisticsService statisticsService, ProgressService progressService, PlayerService playerService){
        this.statisticsService = statisticsService;
        this.progressService = progressService;
        this.playerService = playerService;
    }

    @GetMapping(path="/statistics")
	public String StatisticsList(ModelMap modelMap, @AuthenticationPrincipal UserDetails user) {
		User userp = userService.findUser(user.getUsername());
		modelMap.addAttribute("statistics", statisticsService.listStatistics(userp));
		//modelMap.addAttribute("user", user);
        
		return STATISTICS_VIEW;	
	}

    @GetMapping(path="/ranking")
	public String RankingList(ModelMap modelMap) {
        Map<User,Double> ranking = statisticsService.listRankingUserVictory();
        List<Integer> positions = Arrays.asList(1,2,3,4,5,6,7,8,9,10);
		modelMap.addAttribute("rankingMap", ranking);
		modelMap.addAttribute("rankingNumbers", positions);
		return STATISTICS_VIEW;	
	}
}
