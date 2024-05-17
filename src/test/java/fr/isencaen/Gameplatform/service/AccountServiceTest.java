//package fr.isencaen.Gameplatform.service;
//
//import fr.isencaen.Gameplatform.exceptions.AccountFunctionalException;
//import fr.isencaen.Gameplatform.models.Account;
//import fr.isencaen.Gameplatform.models.dto.AccountDto;
//import fr.isencaen.Gameplatform.models.dto.CreateAccountDto;
//import fr.isencaen.Gameplatform.models.dto.MyAccountDto;
//import fr.isencaen.Gameplatform.repositories.AccountRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//import static org.mockito.Mockito.never;
//
//@ExtendWith(MockitoExtension.class)
//public class AccountServiceTest {
//    @InjectMocks
//    private AccountService accountService;
//
//    @Mock
//    private AccountRepository accountRepository;
//
//    private CreateAccountDto createAccountDto;
//    private Account accountAttentedInDatabase;
//    private AccountDto accountDto;
//    private Account accountsendByController;
//    private MyAccountDto myaccountDto;
//
//    @BeforeEach
//    void setUp() {
//        createAccountDto = new CreateAccountDto("test", "mail", "name", "pass", "M1");
//        accountsendByController = new Account("test", "mail", "name", "pass", "M1");
//        accountAttentedInDatabase = new Account(14,"test", "mail", "name", "pass", "M1", "token", "ROLE_USER");
//        accountDto = new AccountDto(14,"test", "mail", "name", "M1");
//        myaccountDto = new MyAccountDto(14,"test", "mail", "name", "M1", "token");
//    }
//
//    @Test
//    void createAccountSuccessfully() throws AccountFunctionalException {
//        // Given -> setup
//        Mockito.when(accountRepository.save(accountsendByController))
//                .thenReturn(accountAttentedInDatabase);
//
//        // When
//        MyAccountDto result = accountService.createAccount(createAccountDto);
//
//        // Then
//        assertEquals(myaccountDto, result);
//        verify(accountRepository, times(1)).save(any(Account.class));
//    }
//
//    @Test
//    void createAccountWithExistingEmail() {
//        when(accountRepository.existsByMail(createAccountDto.email())).thenReturn(true);
//
//        assertThrows(AccountFunctionalException.class, () -> accountService.createAccount(createAccountDto));
//        verify(accountRepository, never()).save(any(Account.class));
//    }
//
//    @Test
//    void createAccountWithExistingPseudo() {
//        when(accountRepository.existsByMail(createAccountDto.email())).thenReturn(false);
//        when(accountRepository.existsByPseudo(createAccountDto.pseudo())).thenReturn(true);
//
//        assertThrows(AccountFunctionalException.class, () -> accountService.createAccount(createAccountDto));
//        verify(accountRepository, never()).save(any(Account.class));
//    }
//}
