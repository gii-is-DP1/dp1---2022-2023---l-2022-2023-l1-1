package org.springframework.samples.petclinic.progress;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.aspectj.lang.annotation.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.samples.petclinic.achievements.Achievement;
import org.springframework.samples.petclinic.achievements.AchievementRepository;
import org.springframework.samples.petclinic.player.Player;
import org.springframework.samples.petclinic.player.PlayerRepository;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class ProgressServiceTest {
    
    @Mock
    ProgressRepository progressRepository;

    @Mock
    AchievementRepository achievementRepository;

    @Mock
    PlayerRepository playerRepository;

    Player player;
    Achievement a1;
    Achievement a2;
    Achievement a3;
    Progress p1;
    


   

    private Player createPlayer() {
        Player player = new Player();
        return player;
    }

    private Achievement createAchievement(String name, String description) {
        Achievement achievement = new Achievement();
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

        a1 = createAchievement("Achievement 1", "Achievement 1 decription");
        a2 = createAchievement("Achievement 2", "Achievement 2 decription");
        a3 = createAchievement("Achievement 3", "Achievement 3 decription");

        p1 = createProgress(a1, player);

        List<Achievement> allAchievements = new ArrayList<>();
        allAchievements.add(a1);
        allAchievements.add(a2);
        allAchievements.add(a3);

        List<Progress> actualProgress = List.of(p1);

        when(achievementRepository.findAll()).thenReturn(allAchievements);
        when(progressRepository.findProgressByPlayer(any(Player.class))).thenReturn(actualProgress);

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
    public void testGetPlayerProgress() {
        ProgressService progressService = new ProgressService(progressRepository);
        assertTrue(a1.getName() == "Achievement 1");


    }


}
