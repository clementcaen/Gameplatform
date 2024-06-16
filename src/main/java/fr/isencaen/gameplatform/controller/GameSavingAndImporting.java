package fr.isencaen.gameplatform.controller;

import fr.isencaen.gameplatform.service.GameSavingAndImportingService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;import fr.isencaen.gameplatform.service.GameSavingAndImportingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@CrossOrigin(origins = "http://localhost:63343")
@RestController
@RequestMapping("/v1/savinggame")
@SecurityRequirement(name = "bearer Auth")
public class GameSavingAndImporting {

    private static final Logger log = LoggerFactory.getLogger(GameSavingAndImporting.class);
    private final GameSavingAndImportingService gameSavingAndImportingService;

    public GameSavingAndImporting(GameSavingAndImportingService gameSavingAndImportingService) {
        this.gameSavingAndImportingService = gameSavingAndImportingService;
    }

    @GetMapping("/save/{pseudo}")
    public ResponseEntity<byte[]> saveGamePosition(@PathVariable String pseudo) {
        try {
            byte[] gameData = gameSavingAndImportingService.saveGamePosition(pseudo);
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=gamePosition.bin");
            return new ResponseEntity<>(gameData, headers, HttpStatus.OK);
        } catch (IOException e) {
            log.error("Error while saving game position", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/restore/{pseudo}", consumes = "multipart/form-data")
    public ResponseEntity<Void> restoreGamePosition(@PathVariable String pseudo, @RequestParam("file") MultipartFile file) {
        try {
            gameSavingAndImportingService.restoreGamePosition(pseudo, file);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IOException | ClassNotFoundException e) {
            log.error("Error while restoring game position", e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
