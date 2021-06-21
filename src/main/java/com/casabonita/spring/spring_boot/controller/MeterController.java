package com.casabonita.spring.spring_boot.controller;

import com.casabonita.spring.spring_boot.dto.MeterDTO;
import com.casabonita.spring.spring_boot.entity.Meter;
import com.casabonita.spring.spring_boot.service.MeterService;
import com.casabonita.spring.spring_boot.utils.MappingUtilsMeter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api")
public class MeterController {

    @Autowired
    private MeterService meterService;

    @Autowired
    private MappingUtilsMeter mappingUtilsMeter;

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
    public Meter addNewMeter(@RequestBody Meter meter, @RequestParam int meterPlaceNumber){

        meterService.save(meter, meterPlaceNumber);

        return meter;
    }

    @PutMapping(value = "/meters")
    public Meter updateMeter(@RequestBody Meter meter, @RequestParam int meterPlaceNumber){

        meterService.save(meter, meterPlaceNumber);

        return meter;
    }

    @DeleteMapping(value = "/meters/{id}")
    public String deleteMeter(@PathVariable Integer id){

        Meter meter = meterService.findById(id);

        if(meter == null){
            return "There is no Meter with id = " + id + " in DataBase.";
        }

        meterService.deleteById(id);

        return "Meter with id = " + id + " was deleted.";
    }

}
