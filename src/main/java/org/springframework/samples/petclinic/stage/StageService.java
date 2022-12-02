package org.springframework.samples.petclinic.stage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.enums.CurrentStage;
import org.springframework.samples.petclinic.turn.Turn;
import org.springframework.stereotype.Service;

@Service
public class StageService {
    StageRepository stageRepository;

    @Autowired
    public StageService (StageRepository stageRepository) {
        this.stageRepository = stageRepository;
    }

    public void save (Stage stage) {
        stageRepository.save(stage);
    }

    public Stage getStageByTurn (Turn turn) {
        return stageRepository.findStageByTurn(turn);
    }

    public void changeStage (Stage stage, CurrentStage currentStage) { //por ahora no hace falta luego puede que si
        stage.setCurrentStage(currentStage);
        stageRepository.save(stage);

    }

    public void updateStageVotes (Stage actualStage) {
        if (actualStage.getTurn().getVoteCount() == 2) {
            actualStage.setCurrentStage(CurrentStage.VETO);
            stageRepository.save(actualStage);
            }

    }
    
}
