package com.qwon.eat_together.service;

import com.qwon.eat_together.config.UserAccount;
import com.qwon.eat_together.domain.Account;
import com.qwon.eat_together.dto.AlarmDto;
import com.qwon.eat_together.dto.Profile;
import com.qwon.eat_together.dto.PasswordDto;
import com.qwon.eat_together.dto.SignUpDto;
import com.qwon.eat_together.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
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
    private final ModelMapper modelMapper;

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
        modelMapper.map(profile,account); // map.(source,destination)
        // 주의사항 : 인스턴스의 이름이 같아야 함
//        account.setBio(profile.getBio());
//        account.setAge(profile.getAge());
//        account.setJob(profile.getJob());
//        account.setLocation(profile.getLocation());
//        account.setProfileImage(profile.getProfileImage());
        accountRepository.save(account); // account는 detached 객체 -> merge
    }

    public void passwordUpdate(Account account, @Valid PasswordDto passwordDto){
        modelMapper.map(passwordDto,account);
//        account.setPassword(passwordEncoder.encode(passwordDto.getPassword()));
//        account.setPasswordCheck(passwordEncoder.encode(passwordDto.getPasswordCheck()));
        accountRepository.save(account);
    }

    public void updateAlarm(Account account, AlarmDto alarmDto) {
        modelMapper.map(alarmDto,account);
//        account.setCreated(alarmDto.isCreated());
//        account.setResult(alarmDto.isResult());
//        account.setUpdate(alarmDto.isUpdate());
        accountRepository.save(account);
    }
}
