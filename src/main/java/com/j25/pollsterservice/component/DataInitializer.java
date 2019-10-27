package com.j25.pollsterservice.component;

import com.j25.pollsterservice.model.Account;
import com.j25.pollsterservice.model.AccountRole;
import com.j25.pollsterservice.repository.AccountRoleRepository;
import com.j25.pollsterservice.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class DataInitializer implements ApplicationListener<ContextRefreshedEvent> {
    private AccountRepository accountRepository;
    private AccountRoleRepository accountRoleRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public DataInitializer(AccountRepository accountRepository, AccountRoleRepository accountRoleRepository, PasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.accountRoleRepository = accountRoleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        addDefaultRole("USER");
        addDefaultRole("ADMIN");

        addDefaultUser("admin", "admin", "adminName", "adminSurname", "admin@aplication.com","111 111 111" , "ADMIN", "USER");
        addDefaultUser("user", "user", "userName", "userSurname", "user@aplication.com","222 222 222" ,"USER");
    }

    private void addDefaultUser(String username, String password, String name, String surname, String userEmail, String phone, String... roles) {
        if (!accountRepository.existsByUsername(username)) {
            Account account = new Account();
            account.setUsername(username);
            account.setPassword(passwordEncoder.encode(password));
            account.setName(name);
            account.setSurname(surname);
            account.setUserEmail(userEmail);
            account.setPhone(phone);

            Set<AccountRole> userRoles = findRoles(roles);
            account.setAccountRoles(userRoles);

            accountRepository.save(account);
        }
    }

    private Set<AccountRole> findRoles(String[] roles) {
        Set<AccountRole> accountRoles = new HashSet<>();
        for (String role : roles) {
            accountRoleRepository.findByName(role).ifPresent(accountRoles::add);
        }
        return accountRoles;
    }

    private void addDefaultRole(String role) {
        if (!accountRoleRepository.existsByName(role)) {
            AccountRole newRole = new AccountRole();
            newRole.setName(role);

            accountRoleRepository.save(newRole);
        }
    }
}
