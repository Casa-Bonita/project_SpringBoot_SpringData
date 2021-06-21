package com.casabonita.spring.spring_boot.repository;

import com.casabonita.spring.spring_boot.entity.Reading;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReadingRepository extends JpaRepository<Reading, Integer> {

    void deleteReadingByMeter_Id(Integer id);
}
