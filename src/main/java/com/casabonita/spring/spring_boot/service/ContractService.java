package com.casabonita.spring.spring_boot.service;

import com.casabonita.spring.spring_boot.entity.Contract;

import java.util.List;

public interface ContractService {

    List<Contract> findAll();

    void save(Contract contract, int contractPlaceNumber, String renterName);

    Contract findById(Integer id);

    void deleteById(Integer id);

    Contract findContractByNumber(String number);

    Contract findContractByContractPlaceId(Integer id);

    List<Contract> findContractByRenterId(Integer id);
}