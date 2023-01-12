package org.springframework.samples.petclinic.progress;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import org.springframework.data.util.Pair;
import java.util.ArrayList;
import java.util.List;
import org.springframework.samples.petclinic.user.User;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.samples.petclinic.achievements.Achievement;
import org.springframework.samples.petclinic.achievements.AchievementRepository;
import org.springframework.samples.petclinic.enums.AchievementType;
import org.springframework.samples.petclinic.player.Player;
import org.springframework.samples.petclinic.player.PlayerRepository;
import org.springframework.samples.petclinic.player.PlayerService;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class ProgressServiceTest {
    
    @Mock
    ProgressRepository progressRepository;

    @Mock
    AchievementRepository achievementRepository;

    @Mock
    PlayerRepository playerRepository;

    @Mock
    PlayerService playerService;

    Player player;
    Achievement a1;
    Achievement a2;
    Achievement a3;
    Progress p1;
    Progress p2;
    List<Achievement> allAchievements;
    List<Progress> actualProgress;


   

    private Player createPlayer() {
        Player player = new Player();
        player.setUser(new User());
        return player;
    }

    private Achievement createAchievement(String name, String description, AchievementType type) {
        Achievement achievement = new Achievement();
        achievement.setType(type);
        achievement.setName(name);
        achievement.setDescription(description);
        achievement.setThreshold(50.0);
        achievement.setProgress(null);
        return achievement;
    }

    private Progress createProgress(Achievement achievement, Player player) {
        Progress progress = new Progress(achievement, player);
        return progress;
    }

    @BeforeEach
    private void config() {
        player = createPlayer();

        a1 = createAchievement("Achievement 1", "Type duration threshold is 50", AchievementType.TIME);
        a2 = createAchievement("Achievement 2", "Type games threshold is 50", AchievementType.GAMES);

        p1 = createProgress(a1, player);
        p2 = createProgress(a2, player);

        allAchievements = new ArrayList<>();
        allAchievements.add(a1);
        allAchievements.add(a2);
        allAchievements.add(a3);
        actualProgress = new ArrayList<>();
        actualProgress.add(p1);
        actualProgress.add(p2);

        when(achievementRepository.findAll()).thenReturn(allAchievements);
        when(progressRepository.findProgressByPlayer(any(Player.class))).thenReturn(actualProgress);
        when(playerService.getTotalTimePlaying(player.getUser())).thenReturn(10);
        //when(progressRepository.save(any(Progress.class)));

    }

    @Test
    public void testSaveTurnSuccessful() {
        ProgressService progressService = new ProgressService(progressRepository);
        try {
            progressService.saveProgress(p1);;
        } catch (Exception e) {
            fail("no exception should be thrown");
        }
    }

    @Test
    public void testAddAchievementPlayer() { 
        ProgressService progressService = new ProgressService(progressRepository);
        try {
            progressService.addAchievementPlayer(a1, player);;
        } catch (Exception e) {
            fail("no exception should be thrown");
        }

    }
/*/
    @Test
    public void testAchievementProgress() {
        ProgressService progressService = new ProgressService(progressRepository);
        List<Pair<Achievement,Double>> achievementProgress = progressService.achievementProgress(actualProgress);
        
    }*/


}
