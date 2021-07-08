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

class PlaceServiceImplTest {

    AccountRepository accountRepository = Mockito.mock(AccountRepository.class);
    ContractRepository contractRepository = Mockito.mock(ContractRepository.class);
    MeterRepository meterRepository = Mockito.mock(MeterRepository.class);
    PaymentRepository paymentRepository = Mockito.mock(PaymentRepository.class);
    PlaceRepository placeRepository = Mockito.mock(PlaceRepository.class);
    ReadingRepository readingRepository = Mockito.mock(ReadingRepository.class);

    PlaceService placeService = new PlaceServiceImpl(accountRepository, contractRepository, meterRepository, paymentRepository, placeRepository, readingRepository);

    @Test
    public void shouldReturnAllPlaces() {

        List<Place> expected = List.of(new Place());
        Mockito.when(placeRepository.findAll()).thenReturn(expected);

        List<Place> actual = placeService.findAll();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void shouldSavePlace() {

        Place expected = new Place();

        int number = 1;
        String name = "testName";
        double square = 123.45;
        int floor = 1;
        String type = "testType";

        expected.setNumber(number);
        expected.setName(name);
        expected.setSquare(square);
        expected.setFloor(floor);
        expected.setType(type);

        ArgumentCaptor<Place> placeCaptor = ArgumentCaptor.forClass(Place.class);

        placeService.save(expected);

        Mockito.verify(placeRepository, times(1)).save(placeCaptor.capture());

        Place actual = placeCaptor.getValue();

        Assertions.assertEquals(number, actual.getNumber());
        Assertions.assertEquals(name, actual.getName());
        Assertions.assertEquals(square, actual.getSquare());
        Assertions.assertEquals(floor, actual.getFloor());
        Assertions.assertEquals(type, actual.getType());
    }

    @Test
    public void shouldReturnPlaceById() {

        Place expected = new Place();
        Mockito.when(placeRepository.findById(1)).thenReturn(Optional.of(expected));

        Place actual = placeService.findById(1);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void shouldReturnPlaceByNumber() {

        Place expected = new Place();
        int placeNumber = 123;

        Mockito.when(placeRepository.findPlaceByNumber(placeNumber)).thenReturn(expected);
        Place actual = placeService.findPlaceByNumber(placeNumber);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void shouldDeletePlaceById() {

        Place expected = new Place();
        expected.setId(1);

        Mockito.when(placeRepository.findById(1)).thenReturn(Optional.of(expected));

        Mockito.doNothing().when(placeRepository).deleteBy(1);

        placeService.deleteById(1);

        Mockito.verify(placeRepository, times(1)).deleteBy(1);
    }
}