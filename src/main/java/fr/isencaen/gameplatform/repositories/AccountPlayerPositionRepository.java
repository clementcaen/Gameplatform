package fr.isencaen.gameplatform.repositories;
import fr.isencaen.gameplatform.models.AccountPosition;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountPlayerPositionRepository extends JpaRepository<AccountPosition, Integer> {

}
