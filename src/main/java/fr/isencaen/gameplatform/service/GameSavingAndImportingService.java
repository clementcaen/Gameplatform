package fr.isencaen.gameplatform.service;

import fr.isencaen.gameplatform.filter.WhitelistedObjectInputStream;
import fr.isencaen.gameplatform.models.Account;
import fr.isencaen.gameplatform.models.CurrentGame;
import fr.isencaen.gameplatform.repositories.AccountRepository;
import fr.isencaen.gameplatform.repositories.CurrentGameRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.InputStream;

@Service
public class GameSavingAndImportingService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private CurrentGameRepository currentGameRepository;

    public byte[] saveGamePosition(String pseudo) throws IOException {
        Account account = accountRepository.findByPseudo(pseudo);
        if (account == null) {
            throw new IllegalArgumentException("Invalid user pseudo");
        }

        CurrentGame currentGame = currentGameRepository.findByAccount(account).stream().findFirst().orElse(null);
        if (currentGame == null) {
            throw new IllegalArgumentException("No current game found for user");
        }

        // Force initialization of lazy collections
        Hibernate.initialize(currentGame.getAccountPlayersPositions());

        // Serialization
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(currentGame);
        objectOutputStream.close();

        return byteArrayOutputStream.toByteArray();
    }

    public void restoreGamePosition(String pseudo, MultipartFile file) throws IOException, ClassNotFoundException {
        Account account = accountRepository.findByPseudo(pseudo);
        if (account == null) {
            throw new IllegalArgumentException("Invalid user pseudo");
        }

        // Deserialization with whitelisting
        try (InputStream fileInputStream = file.getInputStream();
             WhitelistedObjectInputStream objectInputStream = new WhitelistedObjectInputStream(fileInputStream)) {
            CurrentGame currentGame = (CurrentGame) objectInputStream.readObject();

            // Update the database with the new game position
            currentGameRepository.save(currentGame);
        }
        catch (IOException e) {
            throw new IOException("Error while restoring game position", e);
        }
        catch (ClassNotFoundException e) {
            throw new ClassNotFoundException("Unauthorized class", e);
        }
    }
}
