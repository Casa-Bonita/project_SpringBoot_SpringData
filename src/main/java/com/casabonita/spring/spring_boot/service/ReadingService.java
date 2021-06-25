package com.casabonita.spring.spring_boot.service;

import com.casabonita.spring.spring_boot.entity.Reading;

import java.util.List;

public interface ReadingService {

    List<Reading> findAll();

    Reading save(Reading reading, String meterNumber);

    Reading findById(Integer id);

    void deleteById(Integer id);

    void deleteReadingByMeter_Id(Integer id);
}
