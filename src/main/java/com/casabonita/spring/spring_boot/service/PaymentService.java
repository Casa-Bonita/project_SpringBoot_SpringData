package com.casabonita.spring.spring_boot.service;

import com.casabonita.spring.spring_boot.entity.Payment;

import java.util.List;

public interface PaymentService {

    List<Payment> findAll();

    Payment save(Payment payment, String accountNumber);

    Payment findById(Integer id);

    void deleteById(Integer id);

    void deletePaymentByAccount_Id(Integer id);
}
