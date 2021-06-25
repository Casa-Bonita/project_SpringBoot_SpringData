package com.casabonita.spring.spring_boot.repository;

import com.casabonita.spring.spring_boot.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface AccountRepository extends JpaRepository<Account, Integer> {

    @Modifying
    @Query("delete from Account where id=:id")
    void deleteBy(Integer id);

    Account findAccountByNumber(String number);

    Account findAccountByAccountContract_Id(Integer id);
}
