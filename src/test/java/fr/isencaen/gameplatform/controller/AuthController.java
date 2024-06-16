package fr.isencaen.gameplatform.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.isencaen.gameplatform.exceptions.GamesFunctionalException;
import fr.isencaen.gameplatform.models.*;
import fr.isencaen.gameplatform.models.dto.JwtResponseDTO;
import fr.isencaen.gameplatform.models.dto.LoginDto;
import fr.isencaen.gameplatform.models.dto.MyAccountDtoTokens;
import fr.isencaen.gameplatform.repositories.AccountRepository;
import fr.isencaen.gameplatform.repositories.CurrentGameRepository;
import fr.isencaen.gameplatform.repositories.GameRepository;
import fr.isencaen.gameplatform.service.GameService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@Sql(scripts = "/creationdb-test.sql") // create the database with some object to test
@AutoConfigureMockMvc
public abstract class AuthController {
    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private GameService gameService;


    @Autowired
    private GameRepository gameRepository;

    protected String adminToken;
    protected String userToken;
    protected String adminRefreshToken;
    protected String userRefreshToken;
    @Autowired
    private CurrentGameRepository currentGameRepository;

    @BeforeEach
    void setUp() throws Exception {
        JwtResponseDTO dtoAdmin = obtainAccessToken("Admin1234@", "Admin1234@");
        this.adminToken = dtoAdmin.getAccessToken();
        this.adminRefreshToken = dtoAdmin.getToken();
        JwtResponseDTO dtoUser = obtainAccessToken("User12345@", "User12345@");
        this.userToken = dtoUser.getAccessToken();
        this.userRefreshToken = dtoUser.getToken();
    }

    protected JwtResponseDTO obtainAccessToken(String username, String password) throws Exception {
        LoginDto loginDto = new LoginDto(username, password);

        MvcResult result = mockMvc.perform(post("/v1/connexion")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginDto)))
                .andExpect(status().isOk())
                .andReturn();

        String responseString = result.getResponse().getContentAsString();
        MyAccountDtoTokens tokens = objectMapper.readValue(responseString, MyAccountDtoTokens.class);
        return tokens.jwtResponseDTO();
    }

    protected String getAdminAuthorizationHeader() {
        return "Bearer " + adminToken;
    }

    protected String getUserAuthorizationHeader() {
        return "Bearer " + userToken;
    }

    protected String getUserRefreshToken() {
        return userRefreshToken;
    }
}