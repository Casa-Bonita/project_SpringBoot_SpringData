package com.casabonita.spring.spring_boot.repository;

import com.casabonita.spring.spring_boot.entity.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ContractRepository extends JpaRepository<Contract, Integer> {

    @Modifying
    @Query("delete from Contract where id=:id")
    void deleteBy(Integer id);

    Contract findContractByNumber(String number);

    Contract findContractByContractPlaceId(Integer id);

    List<Contract> findContractByRenterId(Integer id);
}
