package com.casabonita.spring.spring_boot.utils;

import com.casabonita.spring.spring_boot.dto.ReadingDTO;
import com.casabonita.spring.spring_boot.dto.SaveReadingDTO;
import com.casabonita.spring.spring_boot.entity.Reading;
import org.springframework.stereotype.Component;

@Component
public class MappingUtilsReading {

    private final MappingUtilsMeter mappingUtilsMeter;

    public MappingUtilsReading(MappingUtilsMeter mappingUtilsMeter) {
        this.mappingUtilsMeter = mappingUtilsMeter;
    }

    //из Entity в DTO
    public ReadingDTO mapToReadingDTO(Reading reading){
        ReadingDTO dto = new ReadingDTO();
        dto.setId(reading.getId());
        dto.setMeterDTO(mappingUtilsMeter.mapToMeterDTO(reading.getMeter()));
        dto.setTransferData(reading.getTransferData());
        dto.setTransferDate(reading.getTransferDate());

        return dto;
    }

    //из DTO в Entity
    public Reading mapToReading(ReadingDTO dto){
        Reading reading = new Reading();
        reading.setId(dto.getId());
        reading.setMeter(mappingUtilsMeter.mapToMeter(dto.getMeterDTO()));
        reading.setTransferData(dto.getTransferData());
        reading.setTransferDate(dto.getTransferDate());

        return reading;
    }

    //из SavePaymentDTO в Entity
    public Reading mapToReading(SaveReadingDTO saveReadingDTO){
        Reading reading = new Reading();
        reading.setTransferData(saveReadingDTO.getTransferData());
        reading.setTransferDate(saveReadingDTO.getTransferDate());

        return reading;
    }
}
