package com.casabonita.spring.spring_boot.service;

import com.casabonita.spring.spring_boot.entity.Account;
import com.casabonita.spring.spring_boot.repository.MeterRepository;
import com.casabonita.spring.spring_boot.repository.PlaceRepository;
import com.casabonita.spring.spring_boot.entity.Meter;
import com.casabonita.spring.spring_boot.entity.Place;
import com.casabonita.spring.spring_boot.repository.ReadingRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MeterServiceImpl implements MeterService{

    private final MeterRepository meterRepository;
    private final PlaceRepository placeRepository;
    private final ReadingRepository readingRepository;

    public MeterServiceImpl(MeterRepository meterRepository, PlaceRepository placeRepository, ReadingRepository readingRepository) {
        this.meterRepository = meterRepository;
        this.placeRepository = placeRepository;
        this.readingRepository = readingRepository;
    }

    @Override
    public List<Meter> findAll() {

        return meterRepository.findAll();
    }

    @Override
    public void save(Meter meter, int meterPlaceNumber) {

        Meter meterToSave;

        if(meter.getId() == null){
            meterToSave = new Meter();
        }
        else{
            Optional<Meter> optional = meterRepository.findById(meter.getId());
            meterToSave = optional.get();
        }

        meterToSave.setNumber(meter.getNumber());

        Place place = placeRepository.findPlaceByNumber(meterPlaceNumber);
        meterToSave.setMeterPlace(place);

        meterRepository.save(meterToSave);
    }

    @Override
    public Meter findById(Integer id) {

        Meter meter = null;

        Optional<Meter> optional = meterRepository.findById(id);

        if(optional.isPresent()){
            meter = optional.get();
        }

        return meter;
    }

    @Override
    public Meter findMeterByMeterPlace_Id(Integer id) {

        return meterRepository.findMeterByMeterPlace_Id(id);
    }

    @Override
    public Meter findMeterByNumber(String number) {

        return meterRepository.findMeterByNumber(number);
    }

    @Override
    public void deleteById(Integer id) {

        readingRepository.deleteReadingByMeter_Id(id);
        meterRepository.deleteById(id);
    }
}
