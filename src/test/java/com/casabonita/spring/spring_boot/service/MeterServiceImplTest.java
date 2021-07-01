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

class MeterServiceImplTest {

    MeterRepository meterRepository = Mockito.mock(MeterRepository.class);
    PlaceRepository placeRepository = Mockito.mock(PlaceRepository.class);
    ReadingRepository readingRepository = Mockito.mock(ReadingRepository.class);

    MeterService meterService = new MeterServiceImpl(meterRepository, placeRepository, readingRepository);

    @Test
    public void shouldReturnAllMeters() {

        List<Meter> expected = List.of(new Meter());

        Mockito.when(meterRepository.findAll()).thenReturn(expected);

        List<Meter> actual = meterService.findAll();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void shouldSaveMeter() {

        Meter meterExpected = new Meter();
        Place placeExpected = new Place();
        int meterPlaceNumber = 123;
        placeExpected.setNumber(meterPlaceNumber);

        ArgumentCaptor<Meter> requestCaptor = ArgumentCaptor.forClass(Meter.class);
        Mockito.when(placeRepository.findPlaceByNumber(meterPlaceNumber)).thenReturn(placeExpected);

        meterService.save(meterExpected, meterPlaceNumber);

        Mockito.verify(meterRepository, times(1)).save(requestCaptor.capture());
        Meter meterActual = requestCaptor.getValue();
        Assertions.assertEquals(meterPlaceNumber, meterActual.getMeterPlace().getNumber());
        Assertions.assertEquals(placeExpected, meterActual.getMeterPlace());
    }

    @Test
    public void shouldReturnMeterById() {

        Meter expected = new Meter();
        Mockito.when(meterRepository.findById(1)).thenReturn(Optional.of(expected));

        Meter actual = meterService.findById(1);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void shouldReturnMeterByPlaceId() {

        Meter expected = new Meter();
        Mockito.when(meterRepository.findMeterByMeterPlace_Id(1)).thenReturn(expected);

        Meter actual = meterService.findMeterByMeterPlace_Id(1);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void shouldReturnMeterByNumber() {

        Meter expected = new Meter();
        String meterNumber = "TestNumber";

        Mockito.when(meterRepository.findMeterByNumber(meterNumber)).thenReturn(expected);
        Meter actual = meterService.findMeterByNumber(meterNumber);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void shouldDeleteMeterById() {

        Meter expected = new Meter();
        expected.setId(1);

        Mockito.when(meterRepository.findById(1)).thenReturn(Optional.of(expected));

        Mockito.doNothing().when(meterRepository).deleteBy(1);

        meterService.deleteById(1);

        Mockito.verify(meterRepository, times(1)).deleteBy(1);
    }
}