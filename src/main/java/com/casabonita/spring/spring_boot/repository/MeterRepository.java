package com.casabonita.spring.spring_boot.repository;

import com.casabonita.spring.spring_boot.entity.Meter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface MeterRepository extends JpaRepository<Meter, Integer> {

    @Modifying
    @Query("delete from Meter where id=:id")
    void deleteBy(Integer id);

    Meter findMeterByNumber(String number);

    Meter findMeterByMeterPlace_Id(Integer id);
}
