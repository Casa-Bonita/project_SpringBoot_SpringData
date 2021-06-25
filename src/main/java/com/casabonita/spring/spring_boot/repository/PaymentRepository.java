package com.casabonita.spring.spring_boot.repository;

import com.casabonita.spring.spring_boot.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {

    @Modifying
    @Query("delete from Payment where id=:id")
    void deleteBy(Integer id);

    void deletePaymentByAccount_Id(Integer id);
}
