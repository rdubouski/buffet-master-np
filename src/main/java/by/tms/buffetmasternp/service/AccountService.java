package by.tms.buffetmasternp.service;

import by.tms.buffetmasternp.dto.AccountRegDto;
import by.tms.buffetmasternp.entity.Account;
import by.tms.buffetmasternp.entity.Profile;
import by.tms.buffetmasternp.enums.Role;
import by.tms.buffetmasternp.repository.AccountRepository;
import by.tms.buffetmasternp.repository.ProfileRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountService implements UserDetailsService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ProfileRepository profileRepository;

    @Transactional
    public void create(AccountRegDto accountRegDto) {
        Account account = new Account();
        account.setUsername(accountRegDto.getUsername());
        account.setPassword(passwordEncoder.encode(accountRegDto.getPassword()));
        account.getAuthorities().add(Role.ROLE_USER);
        accountRepository.save(account);
        Profile profile = new Profile();
        profile.setAccount(account);
        profile.setFirstName(accountRegDto.getFirstName());
        profile.setLastName(accountRegDto.getLastName());
        profileRepository.save(profile);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var byUsername = accountRepository.findByUsername(username);
        if (byUsername.isPresent()) {
            return byUsername.get();
        }
        throw new UsernameNotFoundException(username);
    }

    public boolean checkUsernameExist(String username) {
        Optional<Account> account = accountRepository.findByUsername(username);
        return account.isPresent();
    }
}
