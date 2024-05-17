package fr.isencaen.Gameplatform.repositories;
import fr.isencaen.Gameplatform.models.CurrentGame;
import fr.isencaen.Gameplatform.models.Game;
import fr.isencaen.Gameplatform.models.PositionsObject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PositionRepository extends JpaRepository<PositionsObject, Integer> {

}
