package com.casabonita.spring.spring_boot.controller;

import com.casabonita.spring.spring_boot.dto.ReadingDTO;
import com.casabonita.spring.spring_boot.dto.SaveReadingDTO;
import com.casabonita.spring.spring_boot.entity.Reading;
import com.casabonita.spring.spring_boot.service.ReadingService;
import com.casabonita.spring.spring_boot.utils.MappingUtilsReading;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api")
public class ReadingController {

    private final ReadingService readingService;
    private final MappingUtilsReading mappingUtilsReading;

    public ReadingController(ReadingService readingService, MappingUtilsReading mappingUtilsReading) {
        this.readingService = readingService;
        this.mappingUtilsReading = mappingUtilsReading;
    }

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
    public ReadingDTO addNewReading(@RequestBody SaveReadingDTO saveReadingDTO){

        Reading reading = mappingUtilsReading.mapToReading(saveReadingDTO);

        reading = readingService.save(reading, saveReadingDTO.getMeterNumber());

        return mappingUtilsReading.mapToReadingDTO(reading);
    }

    @PutMapping(value = "/readings")
    public ReadingDTO updateReading(@RequestBody SaveReadingDTO saveReadingDTO, @PathVariable Integer id){

        Reading reading = mappingUtilsReading.mapToReading(saveReadingDTO);

        reading.setId(id);

        reading = readingService.save(reading, saveReadingDTO.getMeterNumber());

        return mappingUtilsReading.mapToReadingDTO(reading);
    }

    @DeleteMapping(value = "/readings/{id}")
    public String deleteReading(@PathVariable Integer id){

        readingService.deleteById(id);

        return "Reading with id = " + id + " was deleted.";
    }
}
