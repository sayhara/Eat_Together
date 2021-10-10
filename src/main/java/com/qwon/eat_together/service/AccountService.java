package com.qwon.eat_together.service;

import com.qwon.eat_together.config.PasswordConfig;
import com.qwon.eat_together.config.UserAccount;
import com.qwon.eat_together.domain.Account;
import com.qwon.eat_together.domain.Profile;
import com.qwon.eat_together.dto.SignUpDto;
import com.qwon.eat_together.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AccountService implements UserDetailsService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    public Account join(SignUpDto signUpDto) {
        Account account=Account.builder()
                .username(signUpDto.getUsername())
                .email(signUpDto.getEmail())
                .password(passwordEncoder.encode(signUpDto.getPassword()))
                .passwordCheck(passwordEncoder.encode(signUpDto.getPasswordCheck()))
                .build();

        accountRepository.save(account);
        return account;
    }

    public void login(Account account) {
        UsernamePasswordAuthenticationToken token=new UsernamePasswordAuthenticationToken(
                new UserAccount(account),
                account.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_USER"))); // 권한이 있는 사용자
        SecurityContextHolder.getContext().setAuthentication(token);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Account account=accountRepository.findByUsername(username);

        if(account==null){
            throw new UsernameNotFoundException(username);
        }
        return new UserAccount(account);
    }

    public void profileUpdate(Account account, @Valid Profile profile) {
        account.setBio(profile.getBio());
        account.setAge(profile.getAge());
        account.setJob(profile.getJob());
        account.setLocation(profile.getLocation());
        account.setProfileImage(profile.getProfileImage());
        accountRepository.save(account);
    }
}
