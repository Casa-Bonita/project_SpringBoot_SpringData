package com.casabonita.spring.spring_boot.service;

import com.casabonita.spring.spring_boot.entity.Renter;

import java.util.List;

public interface RenterService {

    List<Renter> findAll();

    void save(Renter renter);

    Renter findById(Integer id);

    void deleteById(Integer id);

    Renter findByName(String name);

}
