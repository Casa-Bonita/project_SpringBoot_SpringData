package com.casabonita.spring.spring_boot.service;

import com.casabonita.spring.spring_boot.repository.*;
import com.casabonita.spring.spring_boot.entity.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.times;

class ContractServiceImplTest {

    AccountRepository accountRepository = Mockito.mock(AccountRepository.class);
    ContractRepository contractRepository = Mockito.mock(ContractRepository.class);
    PaymentRepository paymentRepository = Mockito.mock(PaymentRepository.class);
    PlaceRepository placeRepository = Mockito.mock(PlaceRepository.class);
    RenterRepository renterRepository = Mockito.mock(RenterRepository.class);

    ContractService contractService = new ContractServiceImpl(accountRepository, contractRepository, paymentRepository,
            placeRepository, renterRepository);

    @Test
    public void shouldReturnAllContracts() {

        List<Contract> expected = List.of(new Contract());
        Mockito.when(contractRepository.findAll()).thenReturn(expected);

        List<Contract> actual = contractService.findAll();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void shouldSaveContract() {

        Contract contractExpected = new Contract();
        Place placeExpected = new Place();
        Renter renterExpected = new Renter();
        int contractPlaceNumber = 123;
        String contractRenterName = "TestName";

        placeExpected.setNumber(contractPlaceNumber);
        renterExpected.setName(contractRenterName);

        ArgumentCaptor<Contract> requestCaptor = ArgumentCaptor.forClass(Contract.class);
        Mockito.when(placeRepository.findPlaceByNumber(contractPlaceNumber)).thenReturn(placeExpected);

        Mockito.when(renterRepository.findByName(contractRenterName)).thenReturn(renterExpected);

        contractService.save(contractExpected, contractPlaceNumber, contractRenterName);

        Mockito.verify(contractRepository, times(1)).save(requestCaptor.capture());
        Contract contractActual = requestCaptor.getValue();
        Assertions.assertEquals(contractPlaceNumber, contractActual.getContractPlace().getNumber());
        Assertions.assertEquals(contractRenterName, contractActual.getRenter().getName());
        Assertions.assertEquals(placeExpected, contractActual.getContractPlace());
        Assertions.assertEquals(renterExpected, contractActual.getRenter());
    }

    @Test
    public void shouldReturnContractById() {

        Contract expected = new Contract();
        Mockito.when(contractRepository.findById(1)).thenReturn(Optional.of(expected));

        Contract actual = contractService.findById(1);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void shouldReturnContractByPlaceId () {

        Contract expected = new Contract();
        Mockito.when(contractRepository.findContractByContractPlaceId(1)).thenReturn(expected);

        Contract actual = contractService.findContractByContractPlaceId(1);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void shouldReturnContractByRenterId() {

        List<Contract> expected = List.of(new Contract());
        Mockito.when(contractRepository.findContractByRenterId(1)).thenReturn(expected);

        List<Contract> actual = contractService.findContractByRenterId(1);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void shouldReturnContractByNumber() {

        Contract expected = new Contract();
        String contractNumber = "TestNumber";

        Mockito.when(contractRepository.findContractByNumber(contractNumber)).thenReturn(expected);
        Contract actual = contractService.findContractByNumber(contractNumber);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void shouldDeleteContractById() {

        Account accountExpected = new Account();
        int contractId = 1;

        Mockito.when(accountRepository.findAccountByAccountContract_Id(contractId)).thenReturn(accountExpected);

        Contract expected = new Contract();
        expected.setId(1);

        Mockito.when(contractRepository.findById(1)).thenReturn(Optional.of(expected));

        Mockito.doNothing().when(contractRepository).deleteBy(1);

        contractService.deleteById(1);

        Mockito.verify(contractRepository, times(1)).deleteBy(1);
    }
}