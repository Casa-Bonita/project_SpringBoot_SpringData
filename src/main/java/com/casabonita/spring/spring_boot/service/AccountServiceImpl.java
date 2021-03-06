package com.casabonita.spring.spring_boot.service;

import com.casabonita.spring.spring_boot.repository.AccountRepository;
import com.casabonita.spring.spring_boot.repository.ContractRepository;
import com.casabonita.spring.spring_boot.entity.Account;
import com.casabonita.spring.spring_boot.entity.Contract;
import com.casabonita.spring.spring_boot.repository.PaymentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService{

    private final AccountRepository accountRepository;
    private final ContractRepository contractRepository;
    private final PaymentRepository paymentRepository;

    public AccountServiceImpl(AccountRepository accountRepository, ContractRepository contractRepository, PaymentRepository paymentRepository) {
        this.accountRepository = accountRepository;
        this.contractRepository = contractRepository;
        this.paymentRepository = paymentRepository;
    }

    @Override
    public List<Account> findAll() {

        return accountRepository.findAll();
    }

    @Override
    @Transactional
    public Account save(Account account, String contractNumber) {

        Account accountToSave;

        if(account.getId() == null){
            accountToSave = new Account();
        } else{
            accountToSave = accountRepository.findById(account.getId())
                    .orElseThrow(() -> new EntityNotFoundException("Account with " + account.getId() + " not found."));
        }

        accountToSave.setNumber(account.getNumber());

        Contract contract = contractRepository.findContractByNumber(contractNumber);
        accountToSave.setAccountContract(contract);

        return accountRepository.save(accountToSave);
    }

    @Override
    public Account findById(Integer id) {

        Account account = null;

        Optional<Account> optional = accountRepository.findById(id);

        if(optional.isPresent()){
            account = optional.get();
        }

        return account;
    }

    @Override
    public Account findAccountByAccountContract_Id(Integer id) {

        return accountRepository.findAccountByAccountContract_Id(id);
    }

    @Override
    public Account findAccountByNumber(String number) {

        return accountRepository.findAccountByNumber(number);
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {

        Optional<Account> optional = accountRepository.findById(id);

        optional
                .orElseThrow(() -> new EntityNotFoundException("Account with " + id + " not found."));

        paymentRepository.deletePaymentByAccountId(id);
        accountRepository.deleteBy(id);
    }
}