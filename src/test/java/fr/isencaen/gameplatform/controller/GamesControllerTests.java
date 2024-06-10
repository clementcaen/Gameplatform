package fr.isencaen.gameplatform.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.isencaen.gameplatform.models.dto.CurrentGameDto;
import fr.isencaen.gameplatform.service.GameService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

@WebMvcTest(GamesGeneralController.class)
class GamesControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GameService gameService;

    @Test
    @WithMockUser(username = "John Doe", roles = {"USER"})
    void getAllGames_emptyList() throws Exception {
        // Given
        Mockito.when(gameService.getAllGames()).thenReturn(List.of());

        // When
        MockHttpServletResponse response = mockMvc.perform(
                MockMvcRequestBuilders.get("/v1/games")
                        .contentType("application/json")
        ).andReturn().getResponse();

        // Then
        Assertions.assertEquals(200, response.getStatus());
        List<CurrentGameDto> result = new ObjectMapper().readValue(response.getContentAsString(), new TypeReference<>() {
        });
        Assertions.assertTrue(result.isEmpty());
    }

}
