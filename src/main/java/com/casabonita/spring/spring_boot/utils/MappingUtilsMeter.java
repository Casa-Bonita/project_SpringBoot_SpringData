package com.casabonita.spring.spring_boot.utils;

import com.casabonita.spring.spring_boot.dto.MeterDTO;
import com.casabonita.spring.spring_boot.dto.SaveMeterDTO;
import com.casabonita.spring.spring_boot.entity.Meter;
import org.springframework.stereotype.Component;

@Component
public class MappingUtilsMeter {

    private final MappingUtilsPlace mappingUtilsPlace;

    public MappingUtilsMeter(MappingUtilsPlace mappingUtilsPlace) {
        this.mappingUtilsPlace = mappingUtilsPlace;
    }

    //из Entity в DTO
    public MeterDTO mapToMeterDTO(Meter meter){
        MeterDTO dto = new MeterDTO();
        dto.setId(meter.getId());
        dto.setNumber(meter.getNumber());
        dto.setMeterPlaceDTO(mappingUtilsPlace.mapToPlaceDTO(meter.getMeterPlace()));

        return dto;
    }

    //из DTO в Entity
    public Meter mapToMeter(MeterDTO dto){
        Meter meter = new Meter();
        meter.setId(dto.getId());
        meter.setNumber(dto.getNumber());
        meter.setMeterPlace(mappingUtilsPlace.mapToPlace(dto.getMeterPlaceDTO()));

        return meter;
    }

    //из SaveMeterDTO в Entity
    public Meter mapToMeter(SaveMeterDTO saveMeterDTO){
        Meter meter = new Meter();
        meter.setNumber(saveMeterDTO.getNumber());
        meter.setMeterPlace(mappingUtilsPlace.mapToPlace(saveMeterDTO.getMeterPlaceDTO()));

        return meter;
    }
}
