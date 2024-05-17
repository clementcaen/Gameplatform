package fr.isencaen.Gameplatform.controller;

import fr.isencaen.Gameplatform.exceptions.AccountFunctionalException;
import fr.isencaen.Gameplatform.exceptions.GamesFunctionalException;
import fr.isencaen.Gameplatform.exceptions.NotImplementedException;
import fr.isencaen.Gameplatform.models.dto.CreateCurrentGameDto;
import fr.isencaen.Gameplatform.models.dto.CurrentGameDto;
import fr.isencaen.Gameplatform.models.dto.InviteDto;
import fr.isencaen.Gameplatform.models.dto.myGameInfoDto;
import fr.isencaen.Gameplatform.service.GameService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin(origins = "http://localhost:63343")




































































































































































































































































//authorize front-end from phpstorm server
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
    @PostMapping("v1/games/{id_current_game}")
    public CurrentGameDto invite(
            @PathVariable Integer id_current_game,
            @Valid @RequestBody InviteDto inviteDto) throws AccountFunctionalException, GamesFunctionalException {
        return gameService.invite(inviteDto, id_current_game);
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
    @GetMapping("v1/accounts/{id_user}/currentgames")
    public List<myGameInfoDto> getMyGames(@PathVariable String id_user) throws NotImplementedException, AccountFunctionalException {
        return gameService.getMyGames(id_user);
    }

    /**
     *
     * @param id :
     * @param id_player :
     * @return {opponentId1, opponentId2, ...}
     */
    @GetMapping("v1/games/{id}?opponents-of={id_player}")
    public String getOpponentsOfPlayer(@PathVariable String id, @PathVariable String id_player) throws NotImplementedException {
        throw new NotImplementedException();
    }

    @GetMapping("v1/games/{id}?ismyTurn={token_player}")
    public String isMyTurn(@PathVariable String id, @PathVariable String token_player) throws NotImplementedException {
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
