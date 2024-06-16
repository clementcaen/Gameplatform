package fr.isencaen.gameplatform.controller;

import fr.isencaen.gameplatform.exceptions.AccountFunctionalException;
import fr.isencaen.gameplatform.models.Account;
import fr.isencaen.gameplatform.models.RefreshToken;
import fr.isencaen.gameplatform.models.dto.*;
import fr.isencaen.gameplatform.service.AccountService;
import fr.isencaen.gameplatform.service.JwtService;
import fr.isencaen.gameplatform.service.RefreshTokenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AccountControllerTestsUnit {

    @Mock
    private AccountService accountService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtService jwtService;

    @Mock
    private RefreshTokenService refreshTokenService;

    @InjectMocks
    private AccountController accountController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateAccount_Success() throws AccountFunctionalException {
        CreateAccountDto createAccountDto = new CreateAccountDto("user123", "user@example.com", "User", "Password@123", "section");
        MyAccountDto myAccountDto = new MyAccountDto(1, "user123", "user@example.com", "User", "section", "token123");

        when(accountService.createAccount(any(CreateAccountDto.class))).thenReturn(myAccountDto);

        MyAccountDto response = accountController.account(createAccountDto);

        assertNotNull(response);
        assertEquals(myAccountDto, response);
    }

    @Test
    void testCreateAccount_EmailAlreadyUsed() throws AccountFunctionalException {
        CreateAccountDto createAccountDto = new CreateAccountDto("user123", "user@example.com", "User", "Password@123", "section");

        when(accountService.createAccount(any(CreateAccountDto.class))).thenThrow(new AccountFunctionalException("Email already used", "EMAIL_USED"));

        assertThrows(AccountFunctionalException.class, () -> accountController.account(createAccountDto));
    }

    @Test
    void testConnexion_Success() throws AccountFunctionalException {
        LoginDto loginDto = new LoginDto("user123", "Password@123");
        MyAccountDto myAccountDto = new MyAccountDto(1, "user123", "user@example.com", "User", "section", "token123");
        RefreshToken refreshToken = new RefreshToken(1, "refreshToken123", null, null);

        when(accountService.connectAccount(any(LoginDto.class))).thenReturn(myAccountDto);
        when(refreshTokenService.createRefreshToken(anyString())).thenReturn(refreshToken);

        MyAccountDtoTokens response = accountController.connexion(loginDto);

        assertNotNull(response);
        assertEquals(myAccountDto, response.myAccountDto());
        assertEquals("token123", response.jwtResponseDTO().getAccessToken());
        assertEquals("refreshToken123", response.jwtResponseDTO().getToken());
    }
    @Test
    void testRefreshToken_Success() {
        // Given - Create user and save refresh token to database for this user
        Account account = new Account("user123", "user@example.com", "User", "hashedPassword", "section");
        account.setToken("token123");
        account.setUserId(1);

        // Save the account and refresh token in the mock database
        RefreshToken refreshToken = new RefreshToken(1, "refreshToken123", new Date(System.currentTimeMillis() + 6000000).toInstant(), account);

        // When
        RefreshTokenRequestDTO refreshTokenRequestDTO = new RefreshTokenRequestDTO("refreshToken123");
        String newJwtToken = "newJwtToken";

        when(refreshTokenService.findByToken(anyString())).thenReturn(Optional.of(refreshToken));
        when(refreshTokenService.verifyExpiration(any(RefreshToken.class))).thenReturn(refreshToken);
        when(jwtService.generateToken(anyString())).thenReturn(newJwtToken);

        JwtResponseDTO response = accountController.refreshToken(refreshTokenRequestDTO);

        // Then
        assertNotNull(response);
        assertEquals(newJwtToken, response.getAccessToken());
        assertEquals("refreshToken123", response.getToken());
    }

    @Test
    void testRefreshToken_TokenNotInDatabase() {
        RefreshTokenRequestDTO refreshTokenRequestDTO = new RefreshTokenRequestDTO("refreshToken123");

        when(refreshTokenService.findByToken(anyString())).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> accountController.refreshToken(refreshTokenRequestDTO));

        assertEquals("Refresh token is not in database!", exception.getMessage());
    }
}
