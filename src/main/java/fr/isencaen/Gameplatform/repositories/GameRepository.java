package fr.isencaen.Gameplatform.repositories;
import fr.isencaen.Gameplatform.models.Game;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GameRepository extends JpaRepository<Game, Integer> {
    Optional<Game> findByName(String name);
}
