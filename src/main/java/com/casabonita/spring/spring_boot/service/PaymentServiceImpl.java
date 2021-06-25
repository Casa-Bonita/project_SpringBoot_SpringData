package com.casabonita.spring.spring_boot.service;

import com.casabonita.spring.spring_boot.repository.AccountRepository;
import com.casabonita.spring.spring_boot.repository.PaymentRepository;
import com.casabonita.spring.spring_boot.entity.Account;
import com.casabonita.spring.spring_boot.entity.Payment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class PaymentServiceImpl implements PaymentService{

    private final AccountRepository accountRepository;
    private final PaymentRepository paymentRepository;

    public PaymentServiceImpl(AccountRepository accountRepository, PaymentRepository paymentRepository) {
        this.accountRepository = accountRepository;
        this.paymentRepository = paymentRepository;
    }

    @Override
    public List<Payment> findAll() {

        return paymentRepository.findAll();
    }

    @Override
    @Transactional
    public Payment save(Payment payment, String accountNumber) {

        Payment paymentToSave;

        if(payment.getId() == null){
            paymentToSave = new Payment();
        } else{
            paymentToSave = paymentRepository.findById(payment.getId())
                    .orElseThrow(() -> new EntityNotFoundException("Payment with " + payment.getId() + " not found."));
        }

        Account account = accountRepository.findAccountByNumber(accountNumber);
        paymentToSave.setAccount(account);

        paymentToSave.setAmount(payment.getAmount());
        paymentToSave.setDate(payment.getDate());
        paymentToSave.setPurpose(payment.getPurpose());

        return paymentRepository.save(paymentToSave);
    }

    @Override
    public Payment findById(Integer id) {

        Payment payment = null;

        Optional<Payment> optional = paymentRepository.findById(id);

        if(optional.isPresent()){
            payment = optional.get();
        }

        return payment;
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {

        Optional<Payment> optional = paymentRepository.findById(id);

        optional
                .orElseThrow(() -> new EntityNotFoundException("Payment with " + id + " not found."));

        paymentRepository.deleteBy(id);
    }

    @Override
    public void deletePaymentByAccount_Id(Integer id) {

        paymentRepository.deletePaymentByAccount_Id(id);
    }
}
