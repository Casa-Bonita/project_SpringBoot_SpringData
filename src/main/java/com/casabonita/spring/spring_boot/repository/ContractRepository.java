package com.casabonita.spring.spring_boot.repository;

import com.casabonita.spring.spring_boot.entity.Contract;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContractRepository extends JpaRepository<Contract, Integer> {

    Contract findContractByNumber(String number);

    Contract findContractByContractPlaceId(Integer id);

    List<Contract> findContractByRenterId(Integer id);
}
