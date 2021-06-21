package com.casabonita.spring.spring_boot.repository;

import com.casabonita.spring.spring_boot.entity.Meter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeterRepository extends JpaRepository<Meter, Integer> {

    Meter findMeterByNumber(String number);

    Meter findMeterByMeterPlace_Id(Integer id);
}
