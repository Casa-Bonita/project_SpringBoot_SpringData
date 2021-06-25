package com.casabonita.spring.spring_boot.controller;

import com.casabonita.spring.spring_boot.dto.ContractDTO;
import com.casabonita.spring.spring_boot.dto.SaveContractDTO;
import com.casabonita.spring.spring_boot.entity.*;
import com.casabonita.spring.spring_boot.service.ContractService;
import com.casabonita.spring.spring_boot.utils.MappingUtilsContract;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api")
public class ContractController {

    private final ContractService contractService;
    private final MappingUtilsContract mappingUtilsContract;

    public ContractController(ContractService contractService, MappingUtilsContract mappingUtilsContract) {
        this.contractService = contractService;
        this.mappingUtilsContract = mappingUtilsContract;
    }

    @GetMapping (value = "/contracts")
    public List<ContractDTO> showAllContracts(){

        List<Contract> allContracts = contractService.findAll();

        return allContracts.stream()
                .map(mappingUtilsContract::mapToContractDTO)
                .collect(Collectors.toList());
    }

    @GetMapping(value = "/contracts/{id}")
    public ContractDTO getContract(@PathVariable Integer id){

        return mappingUtilsContract.mapToContractDTO(contractService.findById(id));
    }

    @PostMapping(value = "/contracts")
    public ContractDTO addNewContract(@RequestBody SaveContractDTO saveContractDTO){

        Contract contract = mappingUtilsContract.mapToContract(saveContractDTO);

        contract = contractService.save(contract, saveContractDTO.getContractPlaceDTO().getNumber(), saveContractDTO.getRenterDTO().getName());

        return mappingUtilsContract.mapToContractDTO(contract);
    }

    @PutMapping(value = "/contracts/{id}")
    public ContractDTO updateContract(@RequestBody SaveContractDTO saveContractDTO, @PathVariable Integer id){

        Contract contract = mappingUtilsContract.mapToContract(saveContractDTO);

        contract.setId(id);

        contract = contractService.save(contract, saveContractDTO.getContractPlaceDTO().getNumber(), saveContractDTO.getRenterDTO().getName());

        return mappingUtilsContract.mapToContractDTO(contract);
    }

    @DeleteMapping(value = "/contracts/{id}")
    public String deleteContract(@PathVariable Integer id){

        contractService.deleteById(id);

        return "Contract with id = " + id + " was deleted.";
    }
}
