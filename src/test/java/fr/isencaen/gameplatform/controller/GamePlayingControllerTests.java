package fr.isencaen.gameplatform.controller;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class GamePlayingControllerTests extends AuthController {

    @Test
    void move_asAdminUser() throws Exception {
        mockMvc.perform(
                        put("/v1/games/")
                                .header(HttpHeaders.AUTHORIZATION, getAdminAuthorizationHeader())
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());
    }

    @Test
    void move_asRegularUser() throws Exception {
        mockMvc.perform(
                        put("/v1/games/")
                                .header(HttpHeaders.AUTHORIZATION, getUserAuthorizationHeader())
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());
    }

    @Test
    void move_withoutProperAuthentication() throws Exception {
        mockMvc.perform(
                        put("/v1/games/")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isForbidden());
    }

    @Test
    void getCells_asAdminUser() throws Exception {
        mockMvc.perform(
                        get("/v1/games/1/cells")
                                .header(HttpHeaders.AUTHORIZATION, getAdminAuthorizationHeader())
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());
    }

    @Test
    void getCells_asRegularUser() throws Exception {
        mockMvc.perform(
                        get("/v1/games/1/cells")
                                .header(HttpHeaders.AUTHORIZATION, getUserAuthorizationHeader())
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());
    }

    @Test
    void getCells_withoutProperAuthentication() throws Exception {
        mockMvc.perform(
                        get("/v1/games/1/cells")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isForbidden());
    }
}
