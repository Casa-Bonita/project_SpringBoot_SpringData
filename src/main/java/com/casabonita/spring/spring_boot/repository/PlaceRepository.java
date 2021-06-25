package com.casabonita.spring.spring_boot.repository;

import com.casabonita.spring.spring_boot.entity.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PlaceRepository extends JpaRepository<Place, Integer> {

    @Modifying
    @Query("delete from Place where id=:id")
    void deleteBy(Integer id);

    Place findPlaceByNumber(int number);
}
