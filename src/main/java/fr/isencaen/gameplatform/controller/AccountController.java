package fr.isencaen.gameplatform.controller;

import fr.isencaen.gameplatform.exceptions.AccountFunctionalException;
import fr.isencaen.gameplatform.exceptions.NotImplementedException;
import fr.isencaen.gameplatform.models.RefreshToken;
import fr.isencaen.gameplatform.models.dto.*;
import fr.isencaen.gameplatform.service.AccountService;
import fr.isencaen.gameplatform.service.JwtService;
import fr.isencaen.gameplatform.service.RefreshTokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.extern.slf4j.Slf4j;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
//import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

//@CrossOrigin(origins = "http://localhost:63343") //authorize front-end from phpstorm server
@RestController
@Slf4j
public class AccountController {
    private final AccountService accountService;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;

    public AccountController(AccountService accountService, JwtService jwtService, RefreshTokenService refreshTokenService) {
        this.accountService = accountService;
        this.jwtService = jwtService;
        this.refreshTokenService = refreshTokenService;
    }

//    public AccountController(AccountService accountService, JwtService jwtService) {
//        this.accountService = accountService;
//        this.jwtService = jwtService;
//    }
//
//    public AccountController(AccountService accountService) {
//        this.accountService = accountService;
//    }

    //On the inscription page
    @Operation(summary = "Account creation")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Account created", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = MyAccountDto.class))}),
        @ApiResponse(responseCode = "409", description = "Email already used", content = @Content),
        @ApiResponse(responseCode = "409", description = "Username already used", content = @Content)
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("v1/account")
    public MyAccountDto account(//@Valid
             @RequestBody CreateAccDto createAccountDto) throws AccountFunctionalException {
        //log.warn("Account creation:{} {} {} {} {}", createAccountDto.email(), createAccountDto.pseudo(), createAccountDto.pwd(), createAccountDto.email(), createAccountDto.section());
        log.warn("Account creation:{} {} {} {} {}", createAccountDto.getEmail(), createAccountDto.getPseudo(), createAccountDto.getPwd(), createAccountDto.getEmail(), createAccountDto.getSection());
        //return accountService.createAccount(createAccountDto);
        return accountService.createAccount(new CreateAccountDto(createAccountDto.getPseudo(), createAccountDto.getEmail(), createAccountDto.getName(), createAccountDto.getPwd(), createAccountDto.getSection()));
    }
    @PostMapping("v1/test")
    public String test(@RequestBody CreateAccDto createAccountDto) {
        log.warn("Account creation:{} {} {} {} {}", createAccountDto.getEmail(), createAccountDto.getPseudo(), createAccountDto.getPwd(), createAccountDto.getEmail(), createAccountDto.getSection());
        return "test";
    }
    //Connexion
    /**
     * @return {result, id, token}
     */
    @Operation(summary = "Connection")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successful connection", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = MyAccountDto.class))}),
        @ApiResponse(responseCode = "409", description = "Username or email does not exist", content = @Content),
        @ApiResponse(responseCode = "409", description = "Incorrect password", content = @Content)
    })
    @PostMapping("v1/connexion")
    public MyAccountDto connexion(
            @RequestBody LoginDto loginDto
            ) throws AccountFunctionalException {
        log.debug("Account connexion");
        return accountService.connectAccount(loginDto);
    }

    //@Secured("ROLE_ADMIN")
    @GetMapping("v1/account/{id}")
    public String getAccountInfos(@PathVariable String id) throws NotImplementedException {
        throw new NotImplementedException();
    }
    @PostMapping("v1/account/refreshToken")
    public JwtResponseDTO refreshToken(@RequestBody RefreshTokenRequestDTO refreshTokenRequestDTO){
        return refreshTokenService.findByToken(refreshTokenRequestDTO.getToken())
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getAccount)
                .map(accountInfo -> {
                    String accessToken = jwtService.generateToken(accountInfo.getPseudo());
                    return JwtResponseDTO.builder()
                            .accessToken(accessToken)
                            .token(refreshTokenRequestDTO.getToken()).build();
                }).orElseThrow(() ->new RuntimeException("Refresh Token is not in DB..!!"));
    }
}
