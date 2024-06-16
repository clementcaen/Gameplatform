package fr.isencaen.gameplatform.controller;

import fr.isencaen.gameplatform.models.dto.CreateAccountDto;
import fr.isencaen.gameplatform.models.dto.LoginDto;
import fr.isencaen.gameplatform.models.dto.MyAccountDtoTokens;
import fr.isencaen.gameplatform.models.dto.RefreshTokenRequestDTO;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class AccountControllerTests extends AuthController {

    @Test
    void accountCreation() throws Exception {
        CreateAccountDto createAccountDto = new CreateAccountDto(
                "newUser",
                "newuser@example.com",
                "New User",
                "Password@123",
                "section1"
        );

        mockMvc.perform(
                        post("/v1/account")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(createAccountDto))
                )
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.pseudo").value("newUser"))
                .andExpect(jsonPath("$.email").value("newuser@example.com"));
    }

    @Test
    void accountCreation_duplicateEmail() throws Exception {
        // Create and persist a user with the same email
        CreateAccountDto existingAccountDto = new CreateAccountDto(
                "existingUser",
                "User12345@example.com",
                "Existing User",
                "Password@123",
                "section1"
        );

        mockMvc.perform(
                post("/v1/account")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(existingAccountDto))
        ).andExpect(status().isCreated());

        // Attempt to create another user with the same email
        CreateAccountDto createAccountDto = new CreateAccountDto(
                "anotherUser",
                "User12345@example.com", // duplicate email
                "Another User",
                "Password@123",
                "section1"
        );

        mockMvc.perform(
                        post("/v1/account")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(createAccountDto))
                )
                .andExpect(status().isConflict());
    }

    @Test
    void accountCreation_duplicatePseudo() throws Exception {
        // Create and persist a user with the same pseudo
        CreateAccountDto existingAccountDto = new CreateAccountDto(
                "aaaUser12345@",
                "existingemail@example.com",
                "Existing User",
                "Password@123",
                "section1"
        );

        mockMvc.perform(
                post("/v1/account")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(existingAccountDto))
        ).andExpect(status().isCreated());

        // Attempt to create another user with the same pseudo
        CreateAccountDto createAccountDto = new CreateAccountDto(
                "aaaUser12345@", // duplicate pseudo
                "anotheremail@example.com",
                "Another User",
                "Password@123",
                "section1"
        );

        mockMvc.perform(
                        post("/v1/account")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(createAccountDto))
                )
                .andExpect(status().isConflict());
    }

    @Test
    void accountLogin_success() throws Exception {
        LoginDto loginDto = new LoginDto("User12345@", "User12345@");

        MvcResult result = mockMvc.perform(
                        post("/v1/connexion")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(loginDto))
                )
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();

        String responseString = result.getResponse().getContentAsString();
        MyAccountDtoTokens tokens = objectMapper.readValue(responseString, MyAccountDtoTokens.class);
        assertNotNull(tokens.jwtResponseDTO().getAccessToken());
        assertNotNull(tokens.jwtResponseDTO().getToken());
    }

    @Test
    void accountLogin_success_withEmail() throws Exception {
        LoginDto loginDto = new LoginDto("User12345@mail", "User12345@");

        mockMvc.perform(
                        post("/v1/connexion")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(loginDto))
                )
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }


    @Test
    void accountLogin_wrongPassword() throws Exception {
        LoginDto loginDto = new LoginDto("User12345@", "wrongPassword");

        mockMvc.perform(
                        post("/v1/connexion")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(loginDto))
                )
                .andExpect(status().isConflict());
    }

    @Test
    void accountLogin_nonexistentUser() throws Exception {
        LoginDto loginDto = new LoginDto("nonexistentUser", "Password@123");

        mockMvc.perform(
                        post("/v1/connexion")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(loginDto))
                )
                .andExpect(status().isConflict());
    }

    @Test
    void refreshToken_invalidToken() throws Exception {
        String invalidRefreshToken = "invalidRefreshToken";

        mockMvc.perform(
                        post("/v1/account/refreshToken")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(new RefreshTokenRequestDTO(invalidRefreshToken)))
                )
                .andExpect(status().isForbidden());
    }

    @Test
    void getAccountInfos_asRegularUser() throws Exception {
        mockMvc.perform(
                        get("/v1/account/User12345@")
                                .header(HttpHeaders.AUTHORIZATION, getUserAuthorizationHeader())
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isForbidden());
    }

    @Test
    void getAccountInfos_withoutProperAuthentication() throws Exception {
        mockMvc.perform(
                        get("/v1/account/User12345@")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isForbidden());
    }
}