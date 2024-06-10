package fr.isencaen.gameplatform.controller;

import fr.isencaen.gameplatform.models.dto.CellInfoDto;
import fr.isencaen.gameplatform.models.dto.MoveDto;
import fr.isencaen.gameplatform.service.GameService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin(origins = "http://localhost:63343") //authorize front-end from phpstorm server
@RestController
@SecurityRequirement(name = "bearerAuth")
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

    @PutMapping("v1/games/")//to a specific game
    public MoveDto move() {
        return gameService.addMove();
    }
    @Operation(summary = "Retrieval of cells")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cells retrieved", content = @Content),
    })
    @GetMapping("/v1/games/{idCurrentGame}/cells")
    public Map<Integer, Map<String, CellInfoDto>> getCells(@PathVariable Integer idCurrentGame) {
        return gameService.getCellsInRow(idCurrentGame);
    }
}
