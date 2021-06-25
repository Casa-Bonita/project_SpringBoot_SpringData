package com.casabonita.spring.spring_boot.controller;

import com.casabonita.spring.spring_boot.dto.PlaceDTO;
import com.casabonita.spring.spring_boot.dto.SavePlaceDTO;
import com.casabonita.spring.spring_boot.entity.Place;
import com.casabonita.spring.spring_boot.service.PlaceService;
import com.casabonita.spring.spring_boot.utils.MappingUtilsPlace;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api")
public class PlaceController {

    private final PlaceService placeService;
    private final MappingUtilsPlace mappingUtilsPlace;

    public PlaceController(PlaceService placeService, MappingUtilsPlace mappingUtilsPlace) {
        this.placeService = placeService;
        this.mappingUtilsPlace = mappingUtilsPlace;
    }

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
    public PlaceDTO addNewPlace(@RequestBody PlaceDTO placeDTO){

        Place place = mappingUtilsPlace.mapToPlace(placeDTO);

        place = placeService.save(place);

        return mappingUtilsPlace.mapToPlaceDTO(place);
    }

    @PutMapping(value = "/places/{id}")
    public PlaceDTO updatePlace(@RequestBody SavePlaceDTO savePlaceDTO, @PathVariable Integer id){

        Place place = mappingUtilsPlace.mapToPlace(savePlaceDTO);

        place.setId(id);

        place = placeService.save(place);

        return mappingUtilsPlace.mapToPlaceDTO(place);
    }

    @DeleteMapping(value = "/places/{id}")
    public String deletePlace(@PathVariable Integer id){

        placeService.deleteById(id);

        return "Place with id = " + id + " was deleted.";
    }
}
