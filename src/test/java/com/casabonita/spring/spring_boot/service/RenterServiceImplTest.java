package com.casabonita.spring.spring_boot.service;

import com.casabonita.spring.spring_boot.repository.*;
import com.casabonita.spring.spring_boot.entity.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.times;

public class RenterServiceImplTest {

    AccountRepository accountRepository = Mockito.mock(AccountRepository.class);
    ContractRepository contractRepository = Mockito.mock(ContractRepository.class);
    PaymentRepository paymentRepository = Mockito.mock(PaymentRepository.class);
    RenterRepository renterRepository = Mockito.mock(RenterRepository.class);

    RenterService renterService = new RenterServiceImpl(accountRepository, contractRepository, paymentRepository, renterRepository);

    @Test
    public void shouldReturnAllRenters() {

        List<Renter> expected = List.of(new Renter());
        Mockito.when(renterRepository.findAll()).thenReturn(expected);

        List<Renter> actual = renterService.findAll();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void shouldSaveRenter(){

        Renter expected = new Renter();

        Mockito.doNothing().when(renterRepository).save(expected);

        renterService.save(expected);

        Mockito.verify(renterRepository, times(1)).save(expected);
    }

    @Test
    public void shouldReturnRenterById(){

        Renter expected = new Renter();
        Mockito.when(renterRepository.findById(1)).thenReturn(Optional.of(expected));

        Renter actual = renterService.findById(1);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void shouldReturnRenterByName(){

        Renter expected = new Renter();
        String renterName = "TestName";

        Mockito.when(renterRepository.findByName(renterName)).thenReturn(expected);
        Renter actual = renterService.findByName(renterName);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void shouldDeleteRenterById(){

        Renter expected = new Renter();
        expected.setId(1);

        Mockito.when(renterRepository.findById(1)).thenReturn(Optional.of(expected));

        Mockito.doNothing().when(renterRepository).deleteBy(1);

        renterService.deleteById(1);

        Mockito.verify(renterRepository, times(1)).deleteBy(1);
    }
}