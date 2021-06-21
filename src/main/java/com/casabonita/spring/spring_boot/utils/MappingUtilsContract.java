package com.casabonita.spring.spring_boot.utils;

import com.casabonita.spring.spring_boot.dto.ContractDTO;
import com.casabonita.spring.spring_boot.entity.Contract;
import org.springframework.stereotype.Component;

@Component
public class MappingUtilsContract {

    private final MappingUtilsPlace mappingUtilsPlace;
    private final MappingUtilsRenter mappingUtilsRenter;

    public MappingUtilsContract(MappingUtilsPlace mappingUtilsPlace, MappingUtilsRenter mappingUtilsRenter) {
        this.mappingUtilsPlace = mappingUtilsPlace;
        this.mappingUtilsRenter = mappingUtilsRenter;
    }

    //из entity в dto
    public ContractDTO mapToContractDTO(Contract contract){
        ContractDTO dto = new ContractDTO();
        dto.setId(contract.getId());
        dto.setNumber(contract.getNumber());
        dto.setDate(contract.getDate());
        dto.setFare(contract.getFare());
        dto.setStartDate(contract.getStartDate());
        dto.setFinishDate(contract.getFinishDate());
        dto.setPaymentDay(contract.getPaymentDay());
        dto.setContractPlaceDTO(mappingUtilsPlace.mapToPlaceDTO(contract.getContractPlace()));
        dto.setRenterDTO(mappingUtilsRenter.mapToRenterDTO(contract.getRenter()));

        return dto;
    }

    //из dto в entity
    public Contract mapToContract(ContractDTO dto){
        Contract contract = new Contract();
        contract.setId(dto.getId());
        contract.setNumber(dto.getNumber());
        contract.setDate(dto.getDate());
        contract.setFare(dto.getFare());
        contract.setStartDate(dto.getStartDate());
        contract.setFinishDate(dto.getFinishDate());
        contract.setPaymentDay(dto.getPaymentDay());
        contract.setContractPlace(mappingUtilsPlace.mapToPlace(dto.getContractPlaceDTO()));
        contract.setRenter(mappingUtilsRenter.mapToRenter(dto.getRenterDTO()));

        return contract;
    }
}
