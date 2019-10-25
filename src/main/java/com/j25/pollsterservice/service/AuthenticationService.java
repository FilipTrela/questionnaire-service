package com.j25.pollsterservice.service;

import com.j25.pollsterservice.model.Account;
import com.j25.pollsterservice.model.AccountRole;
import com.j25.pollsterservice.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService implements UserDetailsService {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Account> optionalAccount = accountRepository.findByUsername(username);
        if (optionalAccount.isPresent()) {
            Account account = optionalAccount.get();

            String[] roles = account.getAccountRoles()
                    .stream()
                    .map(AccountRole::getName).toArray(String[]::new);

            return User.builder()
                    .username(account.getUsername())
                    .password(account.getPassword())
                    .roles(roles)
                    .accountLocked(account.isLocked())
                    .build();
        }

        throw new UsernameNotFoundException("Username not found.");
    }
}
