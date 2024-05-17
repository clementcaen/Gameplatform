/*package fr.isencaen.Gameplatform.service;

import fr.isencaen.Gameplatform.models.dto.CellInfoDto;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class DroolsService {
    @Autowired
    private KieContainer kieContainer;

    public List<CellInfoDto> suggestCase(List<CellInfoDto> cells, int player_id) {//player_id is not used because of in tictactoe all players have the same possible moves
        KieSession kieSession = kieContainer.newKieSession();
        //for (CellInfoDto cellInfoDto : cells) {
        //    kieSession.insert(cellInfoDto);
        //}
        kieSession.fireAllRules();
        kieSession.dispose();
        return cells;
    }
    public boolean isMoveValid(Map<Integer, Map<String, CellInfoDto>> rowCases, int player_id) {

        return true;
    }
    public boolean playerWin(Map<Integer, Map<String, CellInfoDto>> rowCases, int player_id) {

        return true;
    }
}*/
