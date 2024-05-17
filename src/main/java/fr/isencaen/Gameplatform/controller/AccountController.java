package fr.isencaen.Gameplatform.controller;

import fr.isencaen.Gameplatform.exceptions.AccountFunctionalException;
import fr.isencaen.Gameplatform.exceptions.NotImplementedException;
import fr.isencaen.Gameplatform.models.dto.CreateAccountDto;
import fr.isencaen.Gameplatform.models.dto.LoginDto;
import fr.isencaen.Gameplatform.models.dto.MyAccountDto;
import fr.isencaen.Gameplatform.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:63343") //authorize front-end from phpstorm server
@RestController
public class AccountController {
    private final AccountService accountService;


    public AccountController(AccountService accountService) {
        this.accountService = accountService;

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
    public MyAccountDto connexion(
            @RequestBody LoginDto loginDto
            ) throws AccountFunctionalException {
        return accountService.connectAccount(loginDto);
    }

    @GetMapping("v1/account/{id}")
    public String getAccountInfos(@PathVariable String id) throws NotImplementedException {
        throw new NotImplementedException();
    }
}
