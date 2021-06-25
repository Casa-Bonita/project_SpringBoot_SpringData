package com.casabonita.spring.spring_boot.repository;

import com.casabonita.spring.spring_boot.entity.Renter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface RenterRepository extends JpaRepository<Renter, Integer> {

    @Modifying
    @Query("delete from Renter where id=:id")
    void deleteBy(Integer id);

    Renter findByName(String name);
}
