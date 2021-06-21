package com.casabonita.spring.spring_boot.utils;

import com.casabonita.spring.spring_boot.dto.RenterDTO;
import com.casabonita.spring.spring_boot.entity.Renter;
import org.springframework.stereotype.Component;

@Component
public class MappingUtilsRenter {
    //из entity в dto
    public RenterDTO mapToRenterDTO(Renter renter){
        RenterDTO dto = new RenterDTO();
        dto.setId(renter.getId());
        dto.setName(renter.getName());
        dto.setOgrn(renter.getOgrn());
        dto.setInn(renter.getInn());
        dto.setRegistrDate(renter.getRegistrDate());
        dto.setAddress(renter.getAddress());
        dto.setDirectorName(renter.getDirectorName());
        dto.setContactName(renter.getContactName());
        dto.setPhoneNumber(renter.getPhoneNumber());

        return dto;
    }

    //из dto в entity
    public Renter mapToRenter(RenterDTO dto){
        Renter renter = new Renter();
        renter.setId(dto.getId());
        renter.setName(dto.getName());
        renter.setOgrn(dto.getOgrn());
        renter.setInn(dto.getInn());
        renter.setRegistrDate(dto.getRegistrDate());
        renter.setAddress(dto.getAddress());
        renter.setDirectorName(dto.getDirectorName());
        renter.setContactName(dto.getContactName());
        renter.setPhoneNumber(dto.getPhoneNumber());

        return renter;
    }
}
