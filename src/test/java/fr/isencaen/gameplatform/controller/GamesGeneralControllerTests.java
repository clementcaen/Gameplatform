package fr.isencaen.gameplatform.controller;

import fr.isencaen.gameplatform.models.dto.CreateCurrentGameDto;
import fr.isencaen.gameplatform.models.dto.InviteDto;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class GamesGeneralControllerTests extends AuthController {

    @Test
    void createGames_asAdminUser() throws Exception {
        CreateCurrentGameDto createCurrentGameDto = new CreateCurrentGameDto("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJBZG1pbjEyMzRAIiwiaWF0IjoxNzE4MTMxMTMzLCJleHAiOjE3NDk2NjcxMzN9.Ztl0uMd9t_YyERBEPIsOJGyyPKJqF_BwjF_0sb8WSps", "tictactoe");

        mockMvc.perform(
                        post("/v1/games")
                                .header(HttpHeaders.AUTHORIZATION, getAdminAuthorizationHeader())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(createCurrentGameDto))
                )
                .andExpect(status().isCreated());
    }

    @Test
    void createGames_asRegularUser() throws Exception {
        CreateCurrentGameDto createCurrentGameDto = new CreateCurrentGameDto("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJVc2VyMTIzNDVAIiwiaWF0IjoxNzE4MTMxMDI3LCJleHAiOjE3NDk2NjcwMjd9.GNKMKytuNCUZBUcg4C5eAJtcokm263SvomahwzD4Nso", "tictactoe");

        mockMvc.perform(
                        post("/v1/games")
                                .header(HttpHeaders.AUTHORIZATION, getUserAuthorizationHeader())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(createCurrentGameDto))
                )
                .andExpect(status().isCreated());
    }

    @Test
    void createGames_withoutProperAuthentication() throws Exception {
        CreateCurrentGameDto createCurrentGameDto = new CreateCurrentGameDto("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJVc2VyMTIzNDVAIiwiaWF0IjoxNzE4MTMxMDI3LCJleHAiOjE3NDk2NjcwMjd9.GNKMKytuNCUZBUcg4C5eAJtcokm263SvomahwzD4Nso", "tictactoe");

        mockMvc.perform(
                        post("/v1/games")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(createCurrentGameDto))
                )
                .andExpect(status().isForbidden());
    }

    @Test
    void invite_asAdminUser() throws Exception {
        InviteDto inviteDto = new InviteDto("User12345@");

        mockMvc.perform(
                        post("/v1/games/1")
                                .header(HttpHeaders.AUTHORIZATION, getAdminAuthorizationHeader())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(inviteDto))
                )
                .andExpect(status().isCreated());
    }

    @Test
    void invite_asRegularUser() throws Exception {
        InviteDto inviteDto = new InviteDto("Admin1234@");

        mockMvc.perform(
                        post("/v1/games/1")
                                .header(HttpHeaders.AUTHORIZATION, getUserAuthorizationHeader())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(inviteDto))
                )
                .andExpect(status().isCreated());
    }

    @Test
    void invite_withoutProperAuthentication() throws Exception {
        InviteDto inviteDto = new InviteDto("Admin1234@");

        mockMvc.perform(
                        post("/v1/games/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(inviteDto))
                )
                .andExpect(status().isForbidden());
    }

    @Test
    void getMyGames_asAdminUser() throws Exception {
        mockMvc.perform(
                        get("/v1/accounts/currentgames")
                                .header(HttpHeaders.AUTHORIZATION, getAdminAuthorizationHeader())
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());
    }

    @Test
    void getMyGames_asRegularUser() throws Exception {
        mockMvc.perform(
                        get("/v1/accounts/currentgames")
                                .header(HttpHeaders.AUTHORIZATION, getUserAuthorizationHeader())
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());
    }

    @Test
    void getMyGames_withoutProperAuthentication() throws Exception {
        mockMvc.perform(
                        get("/v1/accounts/currentgames")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isForbidden());
    }


    //testing getinfo game id
    @Test
    void getGameInfos_asAdminUser() throws Exception {
        mockMvc.perform(
                        get("/v1/games/1")
                                .header(HttpHeaders.AUTHORIZATION, getAdminAuthorizationHeader())
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());
    }

    @Test
    void getGameInfos_withoutProperAuthentication() throws Exception {
        mockMvc.perform(
                        get("/v1/games/1")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isForbidden());
    }


}
