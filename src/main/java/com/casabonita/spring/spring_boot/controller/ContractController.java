package com.casabonita.spring.spring_boot.controller;

import com.casabonita.spring.spring_boot.dto.ContractDTO;
import com.casabonita.spring.spring_boot.entity.*;
import com.casabonita.spring.spring_boot.service.ContractService;
import com.casabonita.spring.spring_boot.utils.MappingUtilsContract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api")
public class ContractController {

    @Autowired
    private ContractService contractService;

    @Autowired
    private MappingUtilsContract mappingUtilsContract;

    @GetMapping (value = "/contracts")
    public List<ContractDTO> showAllContracts(){

        List<Contract> allContracts = contractService.findAll();

        return allContracts.stream()
                .map(mappingUtilsContract::mapToContractDTO)
                .collect(Collectors.toList());
    }

    @GetMapping(value = "/contracts/{id}")
    public ContractDTO getContract(@PathVariable Integer id){
        Contract contract = contractService.findById(id);

        return mappingUtilsContract.mapToContractDTO(contractService.findById(id));
    }

    @PostMapping(value = "/contracts")
    public Contract addNewContract(@RequestBody Contract contract, @RequestParam int contractPlaceNumber, @RequestParam String renterName){

        contractService.save(contract, contractPlaceNumber, renterName);

        return contract;
    }

    @PutMapping(value = "/contracts")
    public Contract updateContract(@RequestBody Contract contract, @RequestParam int contractPlaceNumber, @RequestParam String renterName){

        contractService.save(contract, contractPlaceNumber, renterName);

        return contract;
    }

    @DeleteMapping(value = "/contracts/{id}")
    public String deleteContract(@PathVariable Integer id){

        Contract contract = contractService.findById(id);

        if(contract == null){
            return "There is no Contract with id = " + id + " in DataBase.";
        }

        contractService.deleteById(id);

        return "Contract with id = " + id + " was deleted.";
    }

}
