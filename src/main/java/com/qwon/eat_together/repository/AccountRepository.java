package com.qwon.eat_together.repository;

import com.qwon.eat_together.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account,Long> {

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);
}
