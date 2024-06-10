package fr.isencaen.gameplatform.service;

import fr.isencaen.gameplatform.exceptions.NotImplementedException;
import fr.isencaen.gameplatform.exceptions.AccountFunctionalException;
import fr.isencaen.gameplatform.models.Account;
import fr.isencaen.gameplatform.models.dto.AccountDto;
import fr.isencaen.gameplatform.models.dto.CreateAccountDto;
import fr.isencaen.gameplatform.models.dto.LoginDto;
import fr.isencaen.gameplatform.models.dto.MyAccountDto;
import fr.isencaen.gameplatform.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class AccountService implements UserDetailsService {
    @Autowired
    private AccountRepository accountRepository;


    public AccountDto getInfosAccount() throws NotImplementedException {
        throw new NotImplementedException();
    }
    public MyAccountDto createAccount(CreateAccountDto createAccountDto) throws AccountFunctionalException {
        if (accountRepository.existsByMail(createAccountDto.email())) {
            throw new AccountFunctionalException("Mail already used", "DUPLICATE_MAIL");
        } else if (accountRepository.existsByPseudo(createAccountDto.pseudo())) {
            throw new AccountFunctionalException("pseudo already used", "DUPLICATE_PSEUDO");
        }else {
            Account account1 = new Account(createAccountDto);//generate the token directly
            Account accountCreated = accountRepository.save(account1);
            return new MyAccountDto(accountCreated);
        }
    }


    public MyAccountDto connectAccount(LoginDto loginDto) throws AccountFunctionalException {
        if (accountRepository.existsByPseudo(loginDto.pseudo())) {
            Account accountFonded = accountRepository.findByPseudo(loginDto.pseudo());
            if (Objects.equals(accountFonded.getPassword(), Account.hashPwd(loginDto.pwd()))) {
                return new MyAccountDto(accountFonded);
            } else {
                throw new AccountFunctionalException("Wrong password", "WRONG_PASSWORD");
            }
        } else if (accountRepository.existsByMail(loginDto.pseudo())) {
            Account accountFonded = accountRepository.findByMail(loginDto.pseudo());
            if (Objects.equals(accountFonded.getPassword(), Account.hashPwd(loginDto.pwd()))) {
                return new MyAccountDto(accountFonded);
            } else {
                throw new AccountFunctionalException("Wrong password", "WRONG_PASSWORD");
            }
        } else {
            throw new AccountFunctionalException("Account doesn_t exist", "ACCOUNT_DON_T_EXIST");
        }
    }

    public Account loadUserByUsername(String pseudo) {
        return accountRepository.findByPseudo(pseudo);
    }
}
