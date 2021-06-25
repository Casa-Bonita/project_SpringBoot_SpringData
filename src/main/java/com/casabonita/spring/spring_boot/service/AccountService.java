package com.casabonita.spring.spring_boot.service;

import com.casabonita.spring.spring_boot.entity.Account;

import java.util.List;

public interface AccountService {

    List<Account> findAll();

    Account save(Account account, String accountContractNumber);

    Account findById(Integer id);

    void deleteById(Integer id);

    Account findAccountByNumber(String number);

    Account findAccountByAccountContract_Id(Integer id);
}
