package com.casabonita.spring.spring_boot.repository;

import com.casabonita.spring.spring_boot.entity.Place;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlaceRepository extends JpaRepository<Place, Integer> {

    Place findPlaceByNumber(int number);
}
