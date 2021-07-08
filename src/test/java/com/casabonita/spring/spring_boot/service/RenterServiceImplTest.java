package com.casabonita.spring.spring_boot.service;

import com.casabonita.spring.spring_boot.repository.*;
import com.casabonita.spring.spring_boot.entity.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
    public void shouldSaveRenter() throws ParseException {

        Renter expected = new Renter();

        String name = "testName";
        String ogrn = "testOgrn";
        String inn = "testInn";
        String d = "2020-12-05";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = sdf.parse(d);
        String address = "testAdress";
        String directorName = "testDirectorName";
        String contactName = "testContactName";
        String phoneNumber = "testPhoneNumber";

        expected.setName(name);
        expected.setOgrn(ogrn);
        expected.setInn(inn);
        expected.setRegistrDate(date);
        expected.setAddress(address);
        expected.setDirectorName(directorName);
        expected.setContactName(contactName);
        expected.setPhoneNumber(phoneNumber);

        ArgumentCaptor<Renter> renterCaptor = ArgumentCaptor.forClass(Renter.class);

        renterService.save(expected);

        Mockito.verify(renterRepository, times(1)).save(renterCaptor.capture());

        Renter actual = renterCaptor.getValue();

        Assertions.assertEquals(name, actual.getName());
        Assertions.assertEquals(ogrn, actual.getOgrn());
        Assertions.assertEquals(inn, actual.getInn());
        Assertions.assertEquals(date, actual.getRegistrDate());
        Assertions.assertEquals(address, actual.getAddress());
        Assertions.assertEquals(directorName, actual.getDirectorName());
        Assertions.assertEquals(contactName, actual.getContactName());
        Assertions.assertEquals(phoneNumber, actual.getPhoneNumber());

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