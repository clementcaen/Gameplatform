package fr.isencaen.gameplatform.controller;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class GameSavingAndImportingTests extends AuthController {

    @Test
    void saveGamePosition_asAdminUser() throws Exception {
        mockMvc.perform(
                get("/v1/savinggame/save/Admin1234@")
                        .header(HttpHeaders.AUTHORIZATION, getAdminAuthorizationHeader())
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_OCTET_STREAM));
    }

    @Test
    void saveGamePosition_asRegularUser() throws Exception {
        mockMvc.perform(
                get("/v1/savinggame/save/User12345@")
                        .header(HttpHeaders.AUTHORIZATION, getUserAuthorizationHeader())
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isForbidden());
    }

    @Test
    void restoreGamePosition_asAdminUser() throws Exception {
        Path path = Paths.get("src/test/resources/gamePosition.bin");
        String name = "file";
        String originalFileName = "gamePosition.bin";
        String contentType = "application/octet-stream";
        byte[] content = Files.readAllBytes(path);
        MockMultipartFile file = new MockMultipartFile(name,
                originalFileName, contentType, content);

        mockMvc.perform(
                        MockMvcRequestBuilders.multipart("/v1/savinggame/restore/Admin1234@")
                                .file(file)
                                .header(HttpHeaders.AUTHORIZATION, getAdminAuthorizationHeader())
                                .contentType(MediaType.MULTIPART_FORM_DATA)
                )
                .andExpect(status().isOk());
    }

    @Test
    void restoreGamePosition_asAdminUserWithbadfile() throws Exception {
        Path path = Paths.get("src/test/resources/malicious.bin");
        String name = "file";
        String originalFileName = "malicious.bin";
        String contentType = "application/octet-stream";
        byte[] content = Files.readAllBytes(path);
        MockMultipartFile file = new MockMultipartFile(name,
                originalFileName, contentType, content);

        mockMvc.perform(
                        MockMvcRequestBuilders.multipart("/v1/savinggame/restore/Admin1234@")
                                .file(file)
                                .header(HttpHeaders.AUTHORIZATION, getAdminAuthorizationHeader())
                                .contentType(MediaType.MULTIPART_FORM_DATA)
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void restoreGamePosition_asRegularUser() throws Exception {
        Path path = Paths.get("src/test/resources/gamePosition.bin");
        String name = "file";
        String originalFileName = "gamePosition.bin";
        String contentType = "application/octet-stream";
        byte[] content = Files.readAllBytes(path);
        MockMultipartFile file = new MockMultipartFile(name,
                originalFileName, contentType, content);

        mockMvc.perform(
                MockMvcRequestBuilders.multipart("/v1/savinggame/restore/User12345@")
                        .file(file)
                        .header(HttpHeaders.AUTHORIZATION, getUserAuthorizationHeader())
                        .contentType(MediaType.MULTIPART_FORM_DATA)
        )
                .andExpect(status().isForbidden());
    }
}