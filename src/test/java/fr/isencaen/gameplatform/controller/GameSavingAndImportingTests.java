package fr.isencaen.gameplatform.controller;

import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithMockUser;

public class GameSavingAndImportingTests {

    @Test
    @WithMockUser(username = "John Doe", roles = {"ADMIN"})
    void testSaveGame() {
        // Given

        // When

        // Then
    }

    @Test
    @WithMockUser(username = "John Doe", roles = {"USER"})
    void forbiddenSaveGame() {
        // Given

        // When

        // Then
    }
}
