package org.springframework.samples.petclinic.achievements;



import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.util.EntityUtils;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class AchievementServiceTests {
    @Autowired
    private AchievementService achievementService;

    @Test
    void getByIdTest() {
        Achievement achievement3 = this.achievementService.getById(3);
        assertThat(achievement3.getName()).isEqualTo("Jugador Experto");
        assertThat(achievement3.getActualDescription()).startsWith("Has jugado");
        assertThat(achievement3.getThreshold()).isEqualTo(100.00);
    }

    @Test
    void getAchievementsTest() {
        List<Achievement> achievements = this.achievementService.getAchievements();
        assertNotNull(achievements);
        assertFalse(achievements.isEmpty());
        assertEquals(6, achievements.size());
        Achievement achievement1 = EntityUtils.getById(achievements, Achievement.class, 1);
        assertThat(achievement1.getName()).isEqualTo("Jugador Novato");
        Achievement achievement5 = EntityUtils.getById(achievements, Achievement.class, 5);
        assertThat(achievement5.getName()).isEqualTo("Ganador Avanzado");
        

    }

}
