package fr.isencaen.gameplatform.repositories;
import fr.isencaen.gameplatform.models.PositionsObject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PositionRepository extends JpaRepository<PositionsObject, Integer> {

}
