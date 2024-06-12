package fr.isencaen.gameplatform.controller;

import fr.isencaen.gameplatform.exceptions.AccountFunctionalException;
import fr.isencaen.gameplatform.exceptions.GamesFunctionalException;
import fr.isencaen.gameplatform.exceptions.NotImplementedException;
import fr.isencaen.gameplatform.models.dto.CreateCurrentGameDto;
import fr.isencaen.gameplatform.models.dto.CurrentGameDto;
import fr.isencaen.gameplatform.models.dto.InviteDto;
import fr.isencaen.gameplatform.models.dto.MyGameInfoDto;
import fr.isencaen.gameplatform.service.GameService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import jakarta.validation.Valid;
//import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin(origins = "http://localhost:63343") //authorize front-end from phpstorm server
//@Secured("ROLE_USER")
@SecurityRequirement(name = "bearerAuth")
@RestController
public class GamesGeneralController {
    private final GameService gameService;

    public GamesGeneralController(GameService gameService) {
        this.gameService = gameService;
    }

    /**
      *
      *
      * @param {id_player, name_game}
      * @return
      * @throws NotImplementedException
      */
    @Operation(summary = "Creation of a game")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Game created", content = @Content),
        @ApiResponse(responseCode = "409", description = "Game does not exist", content = @Content),
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("v1/games")
    public CurrentGameDto createGames(
            @Valid @RequestBody CreateCurrentGameDto createCurrentGameDto
    ) throws GamesFunctionalException {
        return gameService.createGame(createCurrentGameDto.name_game(), createCurrentGameDto.user_token_creator());
    }
    @Operation(summary = "Invitation to a game")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Invitation sent", content = @Content),
        @ApiResponse(responseCode = "409", description = "Game does not exist", content = @Content),
        @ApiResponse(responseCode = "409", description = "Player does not exist", content = @Content),
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("v1/games/{idCurrentGame}")
    public CurrentGameDto invite(
            @PathVariable Integer idCurrentGame,
            @Valid @RequestBody InviteDto inviteDto) throws GamesFunctionalException {
        return gameService.invite(inviteDto, idCurrentGame);
    }
    /**
     *
     * @return {id_current_game, nameGame, playersIds}
     * @throws NotImplementedException :
     */
    @GetMapping("v1/games/{id}")
    public String getGameInfos(@PathVariable String id) throws NotImplementedException {
        throw new NotImplementedException();
    }

    @Operation(summary = "Retrieval of ongoing games")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Games retrieved", content = @Content),
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("v1/accounts/{idUser}/currentgames")
    public List<MyGameInfoDto> getMyGames(@PathVariable String idUser) throws AccountFunctionalException {
        return gameService.getMyGames(idUser);
    }

    /**
     *
     * @param id :
     * @param idPlayer :
     * @return {opponentId1, opponentId2, ...}
     */
    @GetMapping("v1/games/{id}?opponents-of={idPlayer}")
    public String getOpponentsOfPlayer(@PathVariable String id, @PathVariable String idPlayer) throws NotImplementedException {
        throw new NotImplementedException();
    }

    @GetMapping("v1/games/{id}?ismyTurn={tokenPlayer}")
    public String isMyTurn(@PathVariable String id, @PathVariable String tokenPlayer) throws NotImplementedException {
        //Vérifie que le joueur qui possède le token envoyé est bien dans la partie de id_current_game
        //Regarde si c'est son tour, renvoi true ou false
        throw new NotImplementedException();
    }

    @GetMapping("v1/games/{id}?possible-moves")
    public String getPossibleMoves(@PathVariable String id) throws NotImplementedException {
        //Vérifie le jeux et appelle la fonction avec en parametre, le type de jeu et l'id de la partie
        //Pour  tictactoe, renvoie les cases possibles
        throw new NotImplementedException();
    }

}
