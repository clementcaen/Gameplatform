package fr.isencaen.Gameplatform.controller;

import fr.isencaen.Gameplatform.exceptions.GamesFunctionalException;
import fr.isencaen.Gameplatform.exceptions.NotImplementedException;
import fr.isencaen.Gameplatform.models.dto.CellInfoDto;
import fr.isencaen.Gameplatform.models.dto.CreateMoveDto;
import fr.isencaen.Gameplatform.models.dto.MoveDto;
import fr.isencaen.Gameplatform.service.GameService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin(origins = "http://localhost:63343") //authorize front-end from phpstorm server
@RestController
public class GamePlayingController {
    GameService gameService;


    public GamePlayingController(GameService gameService) {
        this.gameService = gameService;
    }

    /**
     * @param {token_user, move}
     * @return {moveValid: true,
     *                     id_current_game: id_current_game,
     *                     type_piece: "cross",
     *                     x: 2,
     *                     y: 3
     *                 }
     */

    @PutMapping("v1/games/{id_game}")
    public MoveDto move(@PathVariable int id_game,
                        @RequestBody CreateMoveDto createMoveDto) throws GamesFunctionalException {
        return gameService.addMove(id_game, createMoveDto);
    }
    @Operation(summary = "Retrieval of cells")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cells retrieved", content = @Content),
    })
    @GetMapping("/v1/games/{id_current_game}/cells")
    public Map<Integer, Map<String, CellInfoDto>> getCells(@PathVariable Integer id_current_game, @RequestParam Integer player_id) {
        return gameService.getCellsInRow(id_current_game, player_id);
    }
}
