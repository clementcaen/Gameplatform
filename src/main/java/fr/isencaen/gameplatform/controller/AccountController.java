package fr.isencaen.gameplatform.controller;

import fr.isencaen.gameplatform.exceptions.AccountFunctionalException;
import fr.isencaen.gameplatform.models.Account;
import fr.isencaen.gameplatform.models.RefreshToken;
import fr.isencaen.gameplatform.models.dto.*;
import fr.isencaen.gameplatform.service.AccountService;
import fr.isencaen.gameplatform.service.JwtService;
import fr.isencaen.gameplatform.service.RefreshTokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:63343") //authorize front-end from phpstorm server
@RestController
public class AccountController {
    private final AccountService accountService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;

    public AccountController(AccountService accountService, AuthenticationManager authenticationManager, JwtService jwtService, RefreshTokenService refreshTokenService) {
        this.accountService = accountService;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.refreshTokenService = refreshTokenService;
    }

    //On the inscription page
    @Operation(summary = "Account creation")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Account created", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = MyAccountDto.class))}),
        @ApiResponse(responseCode = "409", description = "Email already used", content = @Content),
        @ApiResponse(responseCode = "409", description = "Username already used", content = @Content)
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("v1/account")
    public MyAccountDto account(
            @Valid @RequestBody CreateAccountDto createAccountDto) throws AccountFunctionalException {
        return accountService.createAccount(createAccountDto);
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
//    public MyAccountDto connexion(
//            @RequestBody LoginDto loginDto
//            ) throws AccountFunctionalException {
//        return accountService.connectAccount(loginDto);
//    }
    public MyAccountDtoTokens connexion(@Valid @RequestBody LoginDto loginDto) throws AccountFunctionalException {
        // Get an access token and generate the refreshToken for the user
        MyAccountDto myAccountDto = accountService.connectAccount(loginDto);
        String jwtToken = myAccountDto.token();
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(myAccountDto.pseudo());

        // create the response Dto
        JwtResponseDTO jwtResponseDTO = JwtResponseDTO.builder()
                .accessToken(jwtToken)
                .token(refreshToken.getToken())
                .build();

        return new MyAccountDtoTokens(myAccountDto, jwtResponseDTO);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("v1/account/{pseudo}")
    public Account getAccountInfos(@PathVariable String pseudo) {
        return accountService.loadUserByUsername(pseudo);
    }

    @PostMapping("v1/account/refreshToken")
    public JwtResponseDTO refreshToken(
            @RequestBody RefreshTokenRequestDTO refreshTokenRequestDTO) {
        String requestRefreshToken = refreshTokenRequestDTO.getToken();
        return refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getAccount)
                .map(account -> {
                    String token = jwtService.generateToken(account.getPseudo());
                    accountService.newToken(account, token);
                    return JwtResponseDTO.builder()
                            .accessToken(token)
                            .token(requestRefreshToken)
                            .build();
                })
                .orElseThrow(() -> new RuntimeException("Refresh token is not in database!"));
    }

}
