package com.casabonita.spring.spring_boot.service;

import com.casabonita.spring.spring_boot.entity.Payment;
import com.casabonita.spring.spring_boot.repository.MeterRepository;
import com.casabonita.spring.spring_boot.repository.ReadingRepository;
import com.casabonita.spring.spring_boot.entity.Meter;
import com.casabonita.spring.spring_boot.entity.Reading;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class ReadingServiceImpl implements ReadingService{

    private final MeterRepository meterRepository;
    private final ReadingRepository readingRepository;

    public ReadingServiceImpl(MeterRepository meterRepository, ReadingRepository readingRepository) {
        this.meterRepository = meterRepository;
        this.readingRepository = readingRepository;
    }

    @Override
    public List<Reading> findAll() {

        return readingRepository.findAll();
    }

    @Override
    @Transactional
    public Reading save(Reading reading, String meterNumber) {

        Reading readingToSave;

        if(reading.getId() == null){
            readingToSave = new Reading();
        } else{
            readingToSave = readingRepository.findById(reading.getId())
                    .orElseThrow(() -> new EntityNotFoundException("Reading with " + reading.getId() + " not found."));
        }

        Meter meter = meterRepository.findMeterByNumber(meterNumber);
        readingToSave.setMeter(meter);

        readingToSave.setTransferData(reading.getTransferData());
        readingToSave.setTransferDate(reading.getTransferDate());

        return readingRepository.save(readingToSave);
    }

    @Override
    public Reading findById(Integer id) {

        Reading reading = null;

        Optional<Reading> optional = readingRepository.findById(id);

        if(optional.isPresent()){
            reading = optional.get();
        }

        return reading;
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {

        Optional<Reading> optional = readingRepository.findById(id);

        optional
                .orElseThrow(() -> new EntityNotFoundException("Reading with " + id + " not found."));

        readingRepository.deleteById(id);
    }

    @Override
    public void deleteReadingByMeter_Id(Integer id) {

        readingRepository.deleteReadingByMeter_Id(id);
    }
}
