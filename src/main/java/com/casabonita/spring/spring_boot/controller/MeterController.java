package com.casabonita.spring.spring_boot.controller;

import com.casabonita.spring.spring_boot.dto.MeterDTO;
import com.casabonita.spring.spring_boot.dto.SaveMeterDTO;
import com.casabonita.spring.spring_boot.entity.Meter;
import com.casabonita.spring.spring_boot.service.MeterService;
import com.casabonita.spring.spring_boot.utils.MappingUtilsMeter;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api")
public class MeterController {

    private final MeterService meterService;
    private final MappingUtilsMeter mappingUtilsMeter;

    public MeterController(MeterService meterService, MappingUtilsMeter mappingUtilsMeter) {
        this.meterService = meterService;
        this.mappingUtilsMeter = mappingUtilsMeter;
    }

    @GetMapping (value = "/meters")
    public List<MeterDTO> showAllMeters(){

        List<Meter> allMeters = meterService.findAll();

        return allMeters.stream()
                .map(mappingUtilsMeter::mapToMeterDTO)
                .collect(Collectors.toList());
    }

    @GetMapping(value = "/meters/{id}")
    public MeterDTO getMeter(@PathVariable Integer id){

        return mappingUtilsMeter.mapToMeterDTO(meterService.findById(id));
    }

    @PostMapping(value = "/meters")
    public MeterDTO addNewMeter(@RequestBody SaveMeterDTO saveMeterDTO){

        Meter meter = mappingUtilsMeter.mapToMeter(saveMeterDTO);

        meter = meterService.save(meter, saveMeterDTO.getPlaceNumber());

        return mappingUtilsMeter.mapToMeterDTO(meter);
    }

    @PutMapping(value = "/meters/{id}")
    public MeterDTO updateMeter(@RequestBody SaveMeterDTO saveMeterDTO, @PathVariable Integer id){

        Meter meter = mappingUtilsMeter.mapToMeter(saveMeterDTO);

        meter.setId(id);

        meter = meterService.save(meter, saveMeterDTO.getPlaceNumber());

        return mappingUtilsMeter.mapToMeterDTO(meter);
    }

    @DeleteMapping(value = "/meters/{id}")
    public String deleteMeter(@PathVariable Integer id){

        meterService.deleteById(id);

        return "Meter with id = " + id + " was deleted.";
    }
}
