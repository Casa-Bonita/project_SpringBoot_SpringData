package com.casabonita.spring.spring_boot.utils;

import com.casabonita.spring.spring_boot.dto.PlaceDTO;
import com.casabonita.spring.spring_boot.entity.Place;
import org.springframework.stereotype.Component;

@Component
public class MappingUtilsPlace {
    //из entity в dto
    public PlaceDTO mapToPlaceDTO(Place place){
        PlaceDTO dto = new PlaceDTO();
        dto.setId(place.getId());
        dto.setNumber(place.getNumber());
        dto.setName(place.getName());
        dto.setSquare(place.getSquare());
        dto.setFloor(place.getFloor());
        dto.setType(place.getType());

        return dto;
    }

    //из dto в entity
    public Place mapToPlace(PlaceDTO dto){
        Place place = new Place();
        place.setId(dto.getId());
        place.setNumber(dto.getNumber());
        place.setName(dto.getName());
        place.setSquare(dto.getSquare());
        place.setFloor(dto.getFloor());
        place.setType(dto.getType());

        return place;
    }
}
