package com.casabonita.spring.spring_boot.repository;

import com.casabonita.spring.spring_boot.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {



    void deletePaymentByAccount_Id(Integer id);
}
