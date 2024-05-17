package fr.isencaen.Gameplatform.service;

import fr.isencaen.Gameplatform.exceptions.NotImplementedException;
import fr.isencaen.Gameplatform.exceptions.AccountFunctionalException;
import fr.isencaen.Gameplatform.models.Account;
import fr.isencaen.Gameplatform.models.dto.AccountDto;
import fr.isencaen.Gameplatform.models.dto.CreateAccountDto;
import fr.isencaen.Gameplatform.models.dto.LoginDto;
import fr.isencaen.Gameplatform.models.dto.MyAccountDto;
import fr.isencaen.Gameplatform.repositories.AccountRepository;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class AccountService {
    private final AccountRepository accountRepository;
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public AccountDto getInfosAccount() throws NotImplementedException {
        throw new NotImplementedException();
        //return new AccountDto(1, "pseudo", "email", "name", new Date(0), "pwd", "section", "token");
    }
    public MyAccountDto createAccount(CreateAccountDto createAccountDto) throws AccountFunctionalException {
        if (accountRepository.existsByMail(createAccountDto.email())) {
            throw new AccountFunctionalException("Mail already used", "DUPLICATE_MAIL");
        } else if (accountRepository.existsByPseudo(createAccountDto.pseudo())) {
            throw new AccountFunctionalException("pseudo already used", "DUPLICATE_PSEUDO");
        }else {
            Account account1 = new Account(createAccountDto);
            Account accountCreated = accountRepository.save(account1);
            return new MyAccountDto(accountCreated);
        }
    }


    public MyAccountDto connectAccount(LoginDto loginDto) throws AccountFunctionalException {
        if (accountRepository.existsByPseudo(loginDto.pseudo())) {
            Account account_fonded = accountRepository.findByPseudo(loginDto.pseudo());
            if (Objects.equals(account_fonded.getPwd(), Account.hash_pwd(loginDto.pwd()))) {
                return new MyAccountDto(account_fonded);
            } else {
                throw new AccountFunctionalException("Wrong password", "WRONG_PASSWORD");
            }
        } else if (accountRepository.existsByMail(loginDto.pseudo())) {
            Account account_fonded = accountRepository.findByMail(loginDto.pseudo());
            if (Objects.equals(account_fonded.getPwd(), Account.hash_pwd(loginDto.pwd()))) {
                return new MyAccountDto(account_fonded);
            } else {
                throw new AccountFunctionalException("Wrong password", "WRONG_PASSWORD");
            }
        } else {
            throw new AccountFunctionalException("Account doesn_t exist", "ACCOUNT_DON_T_EXIST");
        }
    }
}
