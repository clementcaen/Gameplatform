package fr.isencaen.gameplatform.controller;

import fr.isencaen.gameplatform.exceptions.AccountFunctionalException;
import fr.isencaen.gameplatform.exceptions.GamesFunctionalException;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin(origins = "http://localhost:63343") //authorize front-end from phpstorm server
//@Secured("ROLE_USER")
@SecurityRequirement(name = "bearerAuth")
@RestController
public class GamesGeneralController {
    private static final Logger log = LoggerFactory.getLogger(GamesGeneralController.class);
    private final GameService gameService;

    public GamesGeneralController(GameService gameService) {
        this.gameService = gameService;
    }

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
            @Valid @RequestBody InviteDto inviteDto) throws GamesFunctionalException{
        return gameService.invite(inviteDto, idCurrentGame);
    }
    /**
     * @return {id_current_game, nameGame, playersIds}
     * @throws GamesFunctionalException :
     */
    @GetMapping("v1/games/{id}")
    public CurrentGameDto getGameInfos(@PathVariable String id) throws GamesFunctionalException {
        return gameService.getGameInfos(id);
    }

    @Operation(summary = "Retrieval of ongoing games")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Games retrieved", content = @Content),
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("v1/accounts/currentgames")
    public List<MyGameInfoDto> getMyGames(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) throws AccountFunctionalException {
        String userToken = extractTokenFromHeader(authorizationHeader);
        return gameService.getMyGames(userToken);
    }

    private String extractTokenFromHeader(String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);
        } else {
            throw new IllegalArgumentException("Invalid Authorization header.");
        }
    }
}
