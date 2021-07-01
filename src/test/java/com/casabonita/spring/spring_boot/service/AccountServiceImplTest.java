package com.casabonita.spring.spring_boot.service;

import com.casabonita.spring.spring_boot.repository.*;
import com.casabonita.spring.spring_boot.entity.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

class AccountServiceImplTest {

    AccountRepository accountRepository = mock(AccountRepository.class);
    ContractRepository contractRepository = mock(ContractRepository.class);
    PaymentRepository paymentRepository = mock(PaymentRepository.class);

    AccountService accountService = new AccountServiceImpl(accountRepository, contractRepository, paymentRepository);

    @Test
    public void shouldReturnAllAccounts() {

        List<Account> expected = List.of(new Account());
        Mockito.when(accountRepository.findAll()).thenReturn(expected);

        List<Account> actual = accountService.findAll();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void shouldSaveAccount() {

        Account accountExpected = new Account();
        Contract contractExpected = new Contract();
        String contractNumber = "TestNumber";
        contractExpected.setNumber(contractNumber);

        ArgumentCaptor<Account> requestCaptor = ArgumentCaptor.forClass(Account.class);
        Mockito.when(contractRepository.findContractByNumber(contractNumber)).thenReturn(contractExpected);

        accountService.save(accountExpected, contractNumber);

        Mockito.verify(accountRepository, times(1)).save(requestCaptor.capture());
        Account accountActual = requestCaptor.getValue();
        Assertions.assertEquals(contractNumber, accountActual.getAccountContract().getNumber());
        Assertions.assertEquals(contractExpected, accountActual.getAccountContract());
    }

    @Test
    public void shouldReturnAccountById() {
        Account expected = new Account();
        Mockito.when(accountRepository.findById(1)).thenReturn(Optional.of(expected));

        Account actual = accountService.findById(1);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void shouldReturnAccountByContractId() {

        Account expected = new Account();
        Mockito.when(accountRepository.findAccountByAccountContract_Id(1)).thenReturn(expected);

        Account actual = accountService.findAccountByAccountContract_Id(1);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void shouldReturnAccountByNumber() {

        Account expected = new Account();
        String accountNumber = "TestNumber";

        Mockito.when(accountRepository.findAccountByNumber(accountNumber)).thenReturn(expected);
        Account actual = accountService.findAccountByNumber(accountNumber);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void shouldDeleteAccountById() {

        Account expected = new Account();
        expected.setId(1);

        Mockito.when(accountRepository.findById(1)).thenReturn(Optional.of(expected));

        Mockito.doNothing().when(accountRepository).deleteBy(1);

        accountService.deleteById(1);

        Mockito.verify(accountRepository, times(1)).deleteBy(1);
    }
}