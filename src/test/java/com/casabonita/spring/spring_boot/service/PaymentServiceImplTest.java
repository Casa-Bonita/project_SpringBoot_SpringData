package com.casabonita.spring.spring_boot.service;

import com.casabonita.spring.spring_boot.repository.*;
import com.casabonita.spring.spring_boot.entity.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.times;

class PaymentServiceImplTest {

    AccountRepository accountRepository = Mockito.mock(AccountRepository.class);
    PaymentRepository paymentRepository = Mockito.mock(PaymentRepository.class);

    PaymentService paymentService = new PaymentServiceImpl(accountRepository, paymentRepository);

    @Test
    public void shouldReturnAllPayments() {

        List<Payment> expected = List.of(new Payment());

        Mockito.when(paymentRepository.findAll()).thenReturn(expected);

        List<Payment> actual = paymentService.findAll();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void shouldSavePayment() {

        Payment paymentExpected = new Payment();
        Account accountExpected = new Account();
        String accountNumber = "TestAccount";
        accountExpected.setNumber(accountNumber);

        ArgumentCaptor<Payment> requestCaptor = ArgumentCaptor.forClass(Payment.class);
        Mockito.when(accountRepository.findAccountByNumber(accountNumber)).thenReturn(accountExpected);

        paymentService.save(paymentExpected, accountNumber);

        Mockito.verify(paymentRepository, times(1)).save(requestCaptor.capture());
        Payment paymentActual = requestCaptor.getValue();
        Assertions.assertEquals(accountNumber, paymentActual.getAccount().getNumber());
        Assertions.assertEquals(accountExpected, paymentActual.getAccount());
    }

    @Test
    public void shouldReturnPaymentById() {

        Payment expected = new Payment();
        Mockito.when(paymentRepository.findById(1)).thenReturn(Optional.of(expected));

        Payment actual = paymentService.findById(1);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void shouldDeletePaymentById() {

        Payment expected = new Payment();
        expected.setId(1);

        Mockito.when(paymentRepository.findById(1)).thenReturn(Optional.of(expected));

        Mockito.doNothing().when(paymentRepository).deleteBy(1);

        paymentService.deleteById(1);

        Mockito.verify(paymentRepository, times(1)).deleteBy(1);
    }

    @Test
    public void shouldDeletePaymentByAccountId() {

        Payment expected = new Payment();
        Account account = new Account();
        account.setId(1);
        expected.setAccount(account);

        Mockito.when(accountRepository.findById(1)).thenReturn(Optional.of(account));

        Mockito.doNothing().when(paymentRepository).deletePaymentByAccountId(1);

        paymentService.deletePaymentByAccount_Id(1);

        Mockito.verify(paymentRepository, times(1)).deletePaymentByAccountId(1);
    }
}