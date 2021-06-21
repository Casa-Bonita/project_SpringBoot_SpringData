package com.casabonita.spring.spring_boot.controller;

import com.casabonita.spring.spring_boot.dto.ReadingDTO;
import com.casabonita.spring.spring_boot.entity.Reading;
import com.casabonita.spring.spring_boot.service.ReadingService;
import com.casabonita.spring.spring_boot.utils.MappingUtilsReading;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api")
public class ReadingController {

    @Autowired
    private ReadingService readingService;

    @Autowired
    private MappingUtilsReading mappingUtilsReading;

    @GetMapping (value = "/readings")
    public List<ReadingDTO> showAllReadings(){

        List<Reading> allReadings = readingService.findAll();

        return allReadings.stream()
                .map(mappingUtilsReading::mapToReadingDTO)
                .collect(Collectors.toList());
    }

    @GetMapping(value = "/readings/{id}")
    public ReadingDTO getReading(@PathVariable Integer id){

        return mappingUtilsReading.mapToReadingDTO(readingService.findById(id));
    }

    @PostMapping(value = "/readings")
    public Reading addNewReading(@RequestBody Reading reading, @RequestParam String meterNumber){

        readingService.save(reading, meterNumber);

        return reading;
    }

    @PutMapping(value = "/readings")
    public Reading updateReading(@RequestBody Reading reading, @RequestParam String meterNumber){

        readingService.save(reading, meterNumber);

        return reading;
    }

    @DeleteMapping(value = "/readings/{id}")
    public String deleteReading(@PathVariable Integer id){

        Reading reading = readingService.findById(id);

        if(reading == null){
            return "There is no Reading with id = " + id + " in DataBase.";
        }

        readingService.deleteById(id);

        return "Reading with id = " + id + " was deleted.";
    }

}
