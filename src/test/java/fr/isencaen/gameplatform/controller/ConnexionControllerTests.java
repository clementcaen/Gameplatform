package fr.isencaen.gameplatform.controller;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.isencaen.gameplatform.exceptions.AccountFunctionalException;
import fr.isencaen.gameplatform.models.Account;
import fr.isencaen.gameplatform.models.RefreshToken;
import fr.isencaen.gameplatform.models.dto.CreateAccountDto;
import fr.isencaen.gameplatform.models.dto.LoginDto;
import fr.isencaen.gameplatform.models.dto.MyAccountDto;
import fr.isencaen.gameplatform.models.dto.RefreshTokenRequestDTO;
import fr.isencaen.gameplatform.service.AccountService;
import fr.isencaen.gameplatform.service.JwtService;
import fr.isencaen.gameplatform.service.RefreshTokenService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;

@WebMvcTest(AccountController.class)
@AutoConfigureMockMvc(addFilters = false)//disable security for tests
class ConnexionControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private AuthenticationManager authenticationManager;

    @MockBean
    private AccountService accountService;

    @MockBean
    private RefreshTokenService refreshTokenService;

    @MockBean
    private JwtService jwtService;

    @Test
    void shouldCreateAccountSuccessfully() throws Exception {
        CreateAccountDto createAccountDto = new CreateAccountDto("testPseudo", "testEmail", "testName", "testPwd", "testSection");
        MyAccountDto myAccountDto = new MyAccountDto(1, "testPseudo", "testEmail", "testName", "testSection", "testToken");

        when(accountService.createAccount(any(CreateAccountDto.class))).thenReturn(myAccountDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/v1/account")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(createAccountDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.pseudo").value("testPseudo"))
                .andExpect(jsonPath("$.email").value("testEmail"))
                .andExpect(jsonPath("$.name").value("testName"))
                .andExpect(jsonPath("$.section").value("testSection"))
                .andExpect(jsonPath("$.token").value("testToken"));
    }

    @Test
    void shouldLoginSuccessfully() throws Exception {
        LoginDto loginDto = new LoginDto("testPseudo", "testPwd");
        MyAccountDto myAccountDto = new MyAccountDto(1, "testPseudo", "testEmail", "testName", "testSection", "testToken");

        when(accountService.connectAccount(any(LoginDto.class))).thenReturn(myAccountDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/v1/connexion")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(loginDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pseudo").value("testPseudo"))
                .andExpect(jsonPath("$.email").value("testEmail"))
                .andExpect(jsonPath("$.name").value("testName"))
                .andExpect(jsonPath("$.section").value("testSection"))
                .andExpect(jsonPath("$.token").value("testToken"));
    }

    @Test
    void shouldRefreshTokenSuccessfully() throws Exception {
        String oldToken = "oldTestToken";
        String newToken = "newTestToken";
        RefreshTokenRequestDTO refreshTokenRequestDTO = new RefreshTokenRequestDTO(oldToken);

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(oldToken);
        refreshToken.setAccount(new Account("testPseudo", "testEmail", "testName", "testPwd", "testSection"));

        when(refreshTokenService.findByToken(oldToken)).thenReturn(Optional.of(refreshToken));
        when(refreshTokenService.verifyExpiration(any(RefreshToken.class))).thenReturn(refreshToken);
        when(jwtService.generateToken("testPseudo")).thenReturn(newToken);

        mockMvc.perform(MockMvcRequestBuilders.post("/v1/account/refreshToken")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(refreshTokenRequestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").value(newToken))
                .andExpect(jsonPath("$.token").value(oldToken));
    }

    @Test
    void shouldReturnConflictWhenAccountCreationFails() throws Exception {
        CreateAccountDto createAccountDto = new CreateAccountDto("testPseudo", "testEmail", "testName", "testPwd", "testSection");

        when(accountService.createAccount(any(CreateAccountDto.class))).thenThrow(new AccountFunctionalException("Email already used", "EMAIL_USED"));

        mockMvc.perform(MockMvcRequestBuilders.post("/v1/account")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(createAccountDto)))
                .andExpect(status().isConflict());
    }

    @Test
    void shouldReturnConflictWhenLoginFails() throws Exception {
        LoginDto loginDto = new LoginDto("testPseudo", "testPwd");

        when(accountService.connectAccount(any(LoginDto.class))).thenThrow(new AccountFunctionalException("Wrong password", "WRONG_PASSWORD"));

        mockMvc.perform(MockMvcRequestBuilders.post("/v1/connexion")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(loginDto)))
                .andExpect(status().isConflict());
    }

    @Test
    void shouldReturnBadRequestWhenRefreshTokenIsInvalid() throws Exception {
        String invalidToken = "invalidToken";
        RefreshTokenRequestDTO refreshTokenRequestDTO = new RefreshTokenRequestDTO(invalidToken);

        when(refreshTokenService.findByToken(invalidToken)).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.post("/v1/account/refreshToken")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(refreshTokenRequestDTO)))
                .andExpect(status().isBadRequest());
    }
}
