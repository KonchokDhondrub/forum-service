package ait.cohort5860.security;

import ait.cohort5860.accounting.dao.UserAccountRepository;
import ait.cohort5860.accounting.model.UserAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImp implements UserDetailsService {
    private final UserAccountRepository userAccountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("Getting user details of: " + username);
        UserAccount userAccount = userAccountRepository.findById(username).orElseThrow(() -> new UsernameNotFoundException(username + ": User not found"));
        Collection<String> roles = userAccount.getRoles()
                .stream()
                .map(r -> "ROLE_" + r.name().toUpperCase())
                .toList();
        return new User(username, userAccount.getPassword(), AuthorityUtils.createAuthorityList(roles));
    }
}
