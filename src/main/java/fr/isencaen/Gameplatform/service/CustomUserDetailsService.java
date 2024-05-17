//package fr.isencaen.Gameplatform.service;
//
//import fr.isencaen.Gameplatform.models.Account;
//import fr.isencaen.Gameplatform.repositories.AccountRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Service
//public class CustomUserDetailsService implements UserDetailsService  {
//
//    CustomUserDetailsService(AccountRepository accountRepository) {
//        this.accountRepository = accountRepository;
//    }
//    private final AccountRepository accountRepository;
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        Account account = accountRepository.findByPseudo(username);
//        if (account == null) {
//            throw new UsernameNotFoundException(username);
//        }
//        return new User(account.getPseudo(), account.getPwd(), getGrantedAuthorities(account.getRole()));
//    }
//
//    private List<GrantedAuthority> getGrantedAuthorities(String role) {
//        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
//        authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
//        return authorities;
//    }
//
//}
