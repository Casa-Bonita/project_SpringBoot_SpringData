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

class ReadingServiceImplTest {

    MeterRepository meterRepository = Mockito.mock(MeterRepository.class);
    ReadingRepository readingRepository = Mockito.mock(ReadingRepository.class);

    ReadingService readingService = new ReadingServiceImpl(meterRepository, readingRepository);

    @Test
    public void shouldReturnAllReadings() {

        List<Reading> expected = List.of(new Reading());

        Mockito.when(readingRepository.findAll()).thenReturn(expected);

        List<Reading> actual = readingService.findAll();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void shouldSaveReading() {

        Reading readingExpected = new Reading();
        Meter meterExpected = new Meter();
        String meterNumber = "TestMeter";
        meterExpected.setNumber(meterNumber);

        ArgumentCaptor<Reading> requestCaptor = ArgumentCaptor.forClass(Reading.class);
        Mockito.when(meterRepository.findMeterByNumber(meterNumber)).thenReturn(meterExpected);

        readingService.save(readingExpected, meterNumber);

        Mockito.verify(readingRepository, times(1)).save(requestCaptor.capture());
        Reading readingActual = requestCaptor.getValue();
        Assertions.assertEquals(meterNumber, readingActual.getMeter().getNumber());
        Assertions.assertEquals(meterExpected, readingActual.getMeter());
    }

    @Test
    public void shouldReturnReadingById() {

        Reading expected = new Reading();
        Mockito.when(readingRepository.findById(1)).thenReturn(Optional.of(expected));

        Reading actual = readingService.findById(1);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void shouldDeleteReadingById() {

        Reading expected = new Reading();
        expected.setId(1);

        Mockito.when(readingRepository.findById(1)).thenReturn(Optional.of(expected));

        Mockito.doNothing().when(readingRepository).deleteBy(1);

        readingService.deleteById(1);

        Mockito.verify(readingRepository, times(1)).deleteBy(1);
    }

    @Test
    public void shouldDeleteReadingByMeterId() {

        Reading expected = new Reading();
        Meter meter = new Meter();
        meter.setId(1);
        expected.setMeter(meter);

        Mockito.when(meterRepository.findById(1)).thenReturn(Optional.of(meter));

        Mockito.doNothing().when(readingRepository).deleteReadingByMeterId(1);

        readingService.deleteReadingByMeter_Id(1);

        Mockito.verify(readingRepository, times(1)).deleteReadingByMeterId(1);
    }
}