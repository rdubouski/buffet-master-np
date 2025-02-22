package by.tms.buffetmasternp.repository;

import by.tms.buffetmasternp.entity.Account;
import by.tms.buffetmasternp.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
    Profile findByAccount(Account account);
}
