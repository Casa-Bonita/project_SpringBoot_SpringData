package com.casabonita.spring.spring_boot.controller;

import com.casabonita.spring.spring_boot.dto.PlaceDTO;
import com.casabonita.spring.spring_boot.entity.Place;
import com.casabonita.spring.spring_boot.service.PlaceService;
import com.casabonita.spring.spring_boot.utils.MappingUtilsPlace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api")
public class PlaceController {

    @Autowired
    private PlaceService placeService;

    @Autowired
    private MappingUtilsPlace mappingUtilsPlace;

    @GetMapping (value = "/places")
    public List<PlaceDTO> showAllPlaces(){

        List<Place> allPlaces = placeService.findAll();

        return allPlaces.stream()
                .map(mappingUtilsPlace::mapToPlaceDTO)
                .collect(Collectors.toList());
    }

    @GetMapping(value = "/places/{id}")
    public PlaceDTO getPlace(@PathVariable Integer id){

        return mappingUtilsPlace.mapToPlaceDTO(placeService.findById(id));
    }

    @PostMapping(value = "/places")
    public Place addNewPlace(@RequestBody Place place){

        placeService.save(place);

        return place;
    }

    @PutMapping(value = "/places")
    public Place updatePlace(@RequestBody Place place){

        placeService.save(place);

        return place;
    }

    @DeleteMapping(value = "/places/{id}")
    public String deletePlace(@PathVariable Integer id){

        Place place = placeService.findById(id);

        if(place == null){
            return "There is no Place with id = " + id + " in DataBase.";
        }

        placeService.deleteById(id);

        return "Place with id = " + id + " was deleted.";
    }
}
