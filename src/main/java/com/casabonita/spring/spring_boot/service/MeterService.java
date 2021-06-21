package com.casabonita.spring.spring_boot.service;

import com.casabonita.spring.spring_boot.entity.Meter;

import java.util.List;

public interface MeterService {

    List<Meter> findAll();

    void save(Meter meter, int meterPlaceNumber);

    Meter findById(Integer id);

    void deleteById(Integer id);

    Meter findMeterByNumber(String number);

    Meter findMeterByMeterPlace_Id(Integer id);
}