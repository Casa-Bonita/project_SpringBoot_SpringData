package com.casabonita.spring.spring_boot.utils;

import com.casabonita.spring.spring_boot.dto.RenterDTO;
import com.casabonita.spring.spring_boot.dto.SaveRenterDTO;
import com.casabonita.spring.spring_boot.entity.Renter;
import org.springframework.stereotype.Component;

@Component
public class MappingUtilsRenter {

    //из Entity в DTO
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

    //из DTO в Entity
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

    //из SaveRenterDTO в Entity
    public Renter mapToRenter(SaveRenterDTO saveRenterDTO){
        Renter renter = new Renter();
        renter.setName(saveRenterDTO.getName());
        renter.setOgrn(saveRenterDTO.getOgrn());
        renter.setInn(saveRenterDTO.getInn());
        renter.setRegistrDate(saveRenterDTO.getRegistrDate());
        renter.setAddress(saveRenterDTO.getAddress());
        renter.setDirectorName(saveRenterDTO.getDirectorName());
        renter.setContactName(saveRenterDTO.getContactName());
        renter.setPhoneNumber(saveRenterDTO.getPhoneNumber());

        return renter;
    }
}
