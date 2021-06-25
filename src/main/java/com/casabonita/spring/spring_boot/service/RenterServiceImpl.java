package com.casabonita.spring.spring_boot.service;

import com.casabonita.spring.spring_boot.entity.Account;
import com.casabonita.spring.spring_boot.entity.Contract;
import com.casabonita.spring.spring_boot.repository.*;
import com.casabonita.spring.spring_boot.entity.Renter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class RenterServiceImpl implements RenterService{

    private final AccountRepository accountRepository;
    private final ContractRepository contractRepository;
    private final PaymentRepository paymentRepository;
    private final RenterRepository renterRepository;

    public RenterServiceImpl(AccountRepository accountRepository, ContractRepository contractRepository,
                              PaymentRepository paymentRepository, RenterRepository renterRepository) {
        this.accountRepository = accountRepository;
        this.contractRepository = contractRepository;
        this.paymentRepository = paymentRepository;
        this.renterRepository = renterRepository;
    }

    @Override
    public List<Renter> findAll() {

        return renterRepository.findAll();
    }

    @Override
    @Transactional
    public Renter save(Renter renter) {

        Renter renterToSave;

        if(renter.getId() == null){
            renterToSave = new Renter();
        }else{
            renterToSave = renterRepository.findById(renter.getId())
                    .orElseThrow(() -> new EntityNotFoundException("Renter with " + renter.getId() + " not found."));
        }

        renterToSave.setName(renter.getName());
        renterToSave.setOgrn(renter.getOgrn());
        renterToSave.setInn(renter.getInn());
        renterToSave.setRegistrDate(renter.getRegistrDate());
        renterToSave.setAddress(renter.getAddress());
        renterToSave.setDirectorName(renter.getDirectorName());
        renterToSave.setContactName(renter.getContactName());
        renterToSave.setPhoneNumber(renter.getPhoneNumber());

        return renterRepository.save(renterToSave);
    }

    @Override
    public Renter findById(Integer id) {

        Renter renter = null;

        Optional<Renter> optional = renterRepository.findById(id);

        if(optional.isPresent()){
            renter = optional.get();
        }

        return renter;
    }

    @Override
    public Renter findByName(String name) {

        return renterRepository.findByName(name);
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {

        Optional<Renter> optional = renterRepository.findById(id);

        optional
                .orElseThrow(() -> new EntityNotFoundException("Renter with " + id + " not found."));

        List<Contract> contractList = contractRepository.findContractByRenterId(id);
        Contract contract;
        Integer contractId;
        Account account;
        Integer accountId;

        if (contractList.isEmpty()) {
            renterRepository.deleteById(id);
        }else{
            for (int i = 0; i < contractList.size(); i++) {
                contract = contractList.get(i);
                contractId = contract.getId();

                account = accountRepository.findAccountByAccountContract_Id(contractId);
                accountId = account.getId();

                if(account == null){
                    contractRepository.deleteById(contractId);
                    renterRepository.deleteById(id);
                }else{
                    paymentRepository.deletePaymentByAccount_Id(accountId);
                    accountRepository.deleteById(accountId);
                    contractRepository.deleteById(contractId);
                    renterRepository.deleteById(id);
                }
            }
        }
    }
}
