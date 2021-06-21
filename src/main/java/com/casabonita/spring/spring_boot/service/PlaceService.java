package com.casabonita.spring.spring_boot.service;

import com.casabonita.spring.spring_boot.entity.Place;

import java.util.List;

public interface PlaceService {

    List<Place> findAll();

    void save(Place place);

    Place findById(Integer id);

    void deleteById(Integer id);

    Place findPlaceByNumber(int number);

}
