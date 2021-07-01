package com.casabonita.spring.spring_boot.service;

import com.casabonita.spring.spring_boot.entity.Account;
import com.casabonita.spring.spring_boot.entity.Contract;
import com.casabonita.spring.spring_boot.entity.Meter;
import com.casabonita.spring.spring_boot.repository.*;
import com.casabonita.spring.spring_boot.entity.Place;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class PlaceServiceImpl implements PlaceService{

    private final AccountRepository accountRepository;
    private final ContractRepository contractRepository;
    private final MeterRepository meterRepository;
    private final PaymentRepository paymentRepository;
    private final PlaceRepository placeRepository;
    private final ReadingRepository readingRepository;

    public PlaceServiceImpl(AccountRepository accountRepository, ContractRepository contractRepository,
                            MeterRepository meterRepository, PaymentRepository paymentRepository,
                            PlaceRepository placeRepository, ReadingRepository readingRepository) {
        this.accountRepository = accountRepository;
        this.contractRepository = contractRepository;
        this.meterRepository = meterRepository;
        this.paymentRepository = paymentRepository;
        this.placeRepository = placeRepository;
        this.readingRepository = readingRepository;
    }

    @Override
    public List<Place> findAll() {

        return placeRepository.findAll();
    }

    @Override
    @Transactional
    public Place save(Place place) {

        Place placeToSave;

        if(place.getId() == null){
            placeToSave =  new Place();
        }else{
            placeToSave = placeRepository.findById(place.getId())
                    .orElseThrow(() -> new EntityNotFoundException("Place with " + place.getId() + " not found."));
        }

        placeToSave.setNumber(place.getNumber());
        placeToSave.setName(place.getName());
        placeToSave.setSquare(place.getSquare());
        placeToSave.setFloor(place.getFloor());
        placeToSave.setType(place.getType());

        return placeRepository.save(placeToSave);
    }

    @Override
    public Place findById(Integer id) {

        Place place = null;

        Optional<Place> optional = placeRepository.findById(id);

        if(optional.isPresent()){
            place = optional.get();
        }

        return place;
    }

    @Override
    public Place findPlaceByNumber(int number) {

        return placeRepository.findPlaceByNumber(number);
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {

        Optional<Place> optional = placeRepository.findById(id);

        optional
                .orElseThrow(() -> new EntityNotFoundException("Place with " + id + " not found."));

        Meter meter = meterRepository.findMeterByMeterPlace_Id(id);
        Integer meterId;

        Contract contract = contractRepository.findContractByContractPlaceId(id);
        Integer contractId;

        if (contract == null){
            if(meter == null){
                placeRepository.deleteBy(id);
            }else{
                meterId = meter.getId();
                readingRepository.deleteReadingByMeterId(meterId);
                meterRepository.deleteBy(meterId);

                placeRepository.deleteBy(id);
            }
        }else{
            Account account = accountRepository.findAccountByAccountContract_Id(contract.getId());
            Integer accountId;

            if(account == null){
                if(meter == null){
                    contractId = contract.getId();
                    contractRepository.deleteBy(contractId);

                    placeRepository.deleteBy(id);
                }else{
                    meterId = meter.getId();
                    readingRepository.deleteReadingByMeterId(meterId);
                    meterRepository.deleteBy(meterId);

                    contractId = contract.getId();
                    contractRepository.deleteBy(contractId);

                    placeRepository.deleteBy(id);
                }
            }else{
                if(meter == null){
                    accountId = account.getId();
                    paymentRepository.deletePaymentByAccountId(accountId);
                    accountRepository.deleteBy(accountId);

                    contractId = contract.getId();
                    contractRepository.deleteBy(contractId);

                    placeRepository.deleteBy(id);
                }else{
                    accountId = account.getId();
                    paymentRepository.deletePaymentByAccountId(accountId);
                    accountRepository.deleteBy(accountId);

                    contractId = contract.getId();
                    contractRepository.deleteBy(contractId);

                    meterId = meter.getId();
                    readingRepository.deleteReadingByMeterId(meterId);
                    meterRepository.deleteBy(meterId);

                    placeRepository.deleteBy(id);
                }
            }
        }
    }
}
