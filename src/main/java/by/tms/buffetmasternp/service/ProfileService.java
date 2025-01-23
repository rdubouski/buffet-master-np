package by.tms.buffetmasternp.service;

import by.tms.buffetmasternp.dto.ProfileDto;
import by.tms.buffetmasternp.entity.Account;
import by.tms.buffetmasternp.entity.Profile;
import by.tms.buffetmasternp.repository.AccountRepository;
import by.tms.buffetmasternp.repository.ProfileRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final AccountRepository accountRepository;

    public ProfileService(ProfileRepository profileRepository, AccountRepository accountRepository) {
        this.profileRepository = profileRepository;
        this.accountRepository = accountRepository;
    }

    public ProfileDto getProfile(Authentication authentication) {
        Optional<Account> accountOptional = accountRepository.findByUsername(authentication.getName());
        if (accountOptional.isPresent()) {
            Account account = accountOptional.get();
            Profile profile = profileRepository.findByAccount(account);
            ProfileDto profileDto = new ProfileDto();
            BeanUtils.copyProperties(profile, profileDto);
            return profileDto;
        } else {
            throw new EntityNotFoundException("Аккаунт не найден");
        }

    }

    public ProfileDto updateProfile(ProfileDto profileDto, Authentication authentication) {
        Optional<Account> accountOptional = accountRepository.findByUsername(authentication.getName());
        if (accountOptional.isPresent()) {
            Account account = accountOptional.get();
            Profile profile = profileRepository.findByAccount(account);
            BeanUtils.copyProperties(profileDto, profile);
            profileRepository.save(profile);
            return profileDto;
        } else {
            throw new EntityNotFoundException("Аккаунт не найден");
        }
    }
}
