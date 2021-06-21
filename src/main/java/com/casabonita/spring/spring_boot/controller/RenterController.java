package com.casabonita.spring.spring_boot.controller;

import com.casabonita.spring.spring_boot.dto.RenterDTO;
import com.casabonita.spring.spring_boot.entity.Renter;
import com.casabonita.spring.spring_boot.service.RenterService;
import com.casabonita.spring.spring_boot.utils.MappingUtilsRenter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api")
public class RenterController {

    @Autowired
    private RenterService renterService;

    @Autowired
    private MappingUtilsRenter mappingUtilsRenter;

    @GetMapping (value = "/renters")
    public List<RenterDTO> showAllRenters(){

        List<Renter> allRenters = renterService.findAll();

        return allRenters.stream()
                .map(mappingUtilsRenter::mapToRenterDTO)
                .collect(Collectors.toList());
    }

    @GetMapping(value = "/renters/{id}")
    public RenterDTO getRenter(@PathVariable Integer id){

        return mappingUtilsRenter.mapToRenterDTO(renterService.findById(id));
    }

    @PostMapping(value = "/renters")
    public RenterDTO addNewRenter(@RequestBody RenterDTO renterDTO){

        Renter renter = mappingUtilsRenter.mapToRenter(renterDTO);

        renterService.save(renter);

        return mappingUtilsRenter.mapToRenterDTO(renterService.findById(renter.getId()));
    }

    @PutMapping(value = "/renters")
    public RenterDTO updateRenter(@RequestBody RenterDTO renterDTO){

        Renter renter = mappingUtilsRenter.mapToRenter(renterDTO);

        renterService.save(renter);

        return mappingUtilsRenter.mapToRenterDTO(renterService.findById(renter.getId()));
    }

    @DeleteMapping(value = "/renters/{id}")
    public String deleteRenter(@PathVariable Integer id){

        Renter renter = renterService.findById(id);

        if(renter == null){
            return "There is no Renter with id = " + id + " in DataBase.";
        }

        renterService.deleteById(id);

        return "Renter with id = " + id + " was deleted.";
    }
}
