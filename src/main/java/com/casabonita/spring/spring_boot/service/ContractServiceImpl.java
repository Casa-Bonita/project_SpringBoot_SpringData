package com.casabonita.spring.spring_boot.service;

import com.casabonita.spring.spring_boot.entity.Account;
import com.casabonita.spring.spring_boot.repository.*;
import com.casabonita.spring.spring_boot.entity.Contract;
import com.casabonita.spring.spring_boot.entity.Place;
import com.casabonita.spring.spring_boot.entity.Renter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContractServiceImpl implements ContractService{

    private final AccountRepository accountRepository;
    private final ContractRepository contractRepository;
    private final PaymentRepository paymentRepository;
    private final PlaceRepository placeRepository;
    private final RenterRepository renterRepository;

    public ContractServiceImpl(AccountRepository accountRepository, ContractRepository contractRepository,
                               PaymentRepository paymentRepository, PlaceRepository placeRepository, RenterRepository renterRepository) {
        this.accountRepository = accountRepository;
        this.contractRepository = contractRepository;
        this.paymentRepository = paymentRepository;
        this.placeRepository = placeRepository;
        this.renterRepository = renterRepository;
    }

    @Override
    public List<Contract> findAll() {

        return contractRepository.findAll();
    }

    @Override
    public void save(Contract contract, int contractPlaceNumber, String renterName) {

        Contract contractToSave;

        if(contract.getId() == null){
            contractToSave = new Contract();
        } else{
            Optional<Contract> optional = contractRepository.findById(contract.getId());
            contractToSave = optional.get();
        }

        contractToSave.setNumber(contract.getNumber());
        contractToSave.setDate(contract.getDate());
        contractToSave.setFare(contract.getFare());
        contractToSave.setStartDate(contract.getStartDate());
        contractToSave.setFinishDate(contract.getFinishDate());
        contractToSave.setPaymentDay(contract.getPaymentDay());

        Place place = placeRepository.findPlaceByNumber(contractPlaceNumber);
        contractToSave.setContractPlace(place);

        Renter renter = renterRepository.findByName(renterName);
        contractToSave.setRenter(renter);

        contractRepository.save(contractToSave);

    }

    @Override
    public Contract findById(Integer id) {

        Contract contract = null;

        Optional<Contract> optional = contractRepository.findById(id);

        if(optional.isPresent()){
            contract = optional.get();
        }

        return contract;
    }

    @Override
    public Contract findContractByContractPlaceId(Integer id) {

        return contractRepository.findContractByContractPlaceId(id);
    }

    @Override
    public List<Contract> findContractByRenterId(Integer id) {

        return contractRepository.findContractByRenterId(id);
    }

    @Override
    public Contract findContractByNumber(String number) {

        return contractRepository.findContractByNumber(number);
    }

    @Override
    public void deleteById(Integer id) {

        Account account = accountRepository.findAccountByAccountContract_Id(id);
        Integer accountId = account.getId();

        if(account == null){
            contractRepository.deleteById(id);
        }else{
            paymentRepository.deletePaymentByAccount_Id(accountId);
            accountRepository.deleteById(accountId);
            contractRepository.deleteById(id);
        }
    }
}
