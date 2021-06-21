package com.casabonita.spring.spring_boot.repository;

import com.casabonita.spring.spring_boot.entity.Renter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RenterRepository extends JpaRepository<Renter, Integer> {

    Renter findByName(String name);
}
