package fr.isencaen.gameplatform.repositories;
import fr.isencaen.gameplatform.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Integer> {

    boolean existsByMail(String mail);

    boolean existsByPseudo(String pseudo);

    /**
     * @param pseudo the pseudo of the account
     * @return the account from the pseudo
     */
    Account findByPseudo(String pseudo);

    Account findByMail(String mail);

    Account findByToken(String token);
}
