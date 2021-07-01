package com.casabonita.spring.spring_boot.repository;

import com.casabonita.spring.spring_boot.entity.Reading;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ReadingRepository extends JpaRepository<Reading, Integer> {

    @Modifying
    @Query("delete from Reading where id=:id")
    void deleteBy(Integer id);

    @Modifying
    @Query("delete from Reading where meter.id=:id")
    void deleteReadingByMeterId(Integer id);
}
