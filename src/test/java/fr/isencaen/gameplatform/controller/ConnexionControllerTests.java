package fr.isencaen.gameplatform.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.isencaen.gameplatform.exceptions.AccountFunctionalException;
import fr.isencaen.gameplatform.models.dto.CreateAccountDto;
import fr.isencaen.gameplatform.models.dto.LoginDto;
import fr.isencaen.gameplatform.models.dto.MyAccountDto;
import fr.isencaen.gameplatform.security.JwtServiceConfig;
import fr.isencaen.gameplatform.service.AccountService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Map;

import static org.mockito.ArgumentMatchers.any;

@Import(JwtServiceConfig.class)

@WebMvcTest(AccountController.class)

class ConnexionControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountService accountService;
    @Test
    void shouldCreateAccountSuccessfully() throws Exception {
        CreateAccountDto createAccountDto = new CreateAccountDto("testPseudo", "testEmail", "testName", "testPwd", "testSection");
        MyAccountDto myAccountDto = new MyAccountDto(1, "testPseudo", "testEmail", "testName", "testSection", "testToken");

        Mockito.when(accountService.createAccount(any(CreateAccountDto.class))).thenReturn(myAccountDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/v1/account")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(createAccountDto)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.pseudo").value("testPseudo"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("testEmail"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("testName"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.section").value("testSection"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.token").value("testToken"));
    }

    @Test
    void shouldReturnConflictWhenCreatingAccountWithExistingEmail() throws Exception {
        CreateAccountDto createAccountDto = new CreateAccountDto("testPseudo", "testEmail", "testName", "testPwd", "testSection");

        Mockito.when(accountService.createAccount(any(CreateAccountDto.class))).thenThrow(new AccountFunctionalException("Email already used", "DUPLICATE_MAIL"));

        mockMvc.perform(MockMvcRequestBuilders.post("/v1/account")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(createAccountDto)))
                .andExpect(MockMvcResultMatchers.status().isConflict());
    }

    @Test
    void shouldReturnConflictWhenCreatingAccountWithExistingPseudo() throws Exception {
        CreateAccountDto createAccountDto = new CreateAccountDto("testPseudo", "testEmail", "testName", "testPwd", "testSection");

        Mockito.when(accountService.createAccount(any(CreateAccountDto.class))).thenThrow(new AccountFunctionalException("Pseudo already used", "DUPLICATE_PSEUDO"));

        mockMvc.perform(MockMvcRequestBuilders.post("/v1/account")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(createAccountDto)))
                .andExpect(MockMvcResultMatchers.status().isConflict());
    }

    @Test
    void shouldConnectAccountSuccessfully() throws Exception {
        LoginDto loginDto = new LoginDto("testPseudo", "testPwd");
        MyAccountDto myAccountDto = new MyAccountDto(1, "testPseudo", "testEmail", "testName", "testSection", "testToken");

        Mockito.when(accountService.connectAccount(any(LoginDto.class))).thenReturn(myAccountDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/v1/connexion")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(loginDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.pseudo").value("testPseudo"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("testEmail"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("testName"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.section").value("testSection"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.token").value("testToken"));
    }

    @Test
    void shouldReturnConflictWhenConnectingWithNonExistingAccount() throws Exception {
        LoginDto loginDto = new LoginDto("testPseudo", "testPwd");

        Mockito.when(accountService.connectAccount(any(LoginDto.class))).thenThrow(new AccountFunctionalException("Account doesn't exist", "ACCOUNT_DON'T_EXIST"));

        mockMvc.perform(MockMvcRequestBuilders.post("/v1/connexion")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(loginDto)))
                .andExpect(MockMvcResultMatchers.status().isConflict());
    }

    @Test
    void shouldReturnConflictWhenConnectingWithWrongPassword() throws Exception {
        LoginDto loginDto = new LoginDto("testPseudo", "testPwd");

        Mockito.when(accountService.connectAccount(any(LoginDto.class))).thenThrow(new AccountFunctionalException("Wrong password", "WRONG_PASSWORD"));

        mockMvc.perform(MockMvcRequestBuilders.post("/v1/connexion")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(loginDto)))
                .andExpect(MockMvcResultMatchers.status().isConflict());
    }

}
