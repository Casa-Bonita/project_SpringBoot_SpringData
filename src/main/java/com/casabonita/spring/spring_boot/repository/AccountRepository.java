package com.casabonita.spring.spring_boot.repository;

import com.casabonita.spring.spring_boot.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Integer> {

    Account findAccountByNumber(String number);

    Account findAccountByAccountContract_Id(Integer id);
}
