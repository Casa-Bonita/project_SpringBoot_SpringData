package com.casabonita.spring.spring_boot.controller;

import com.casabonita.spring.spring_boot.dto.RenterDTO;
import com.casabonita.spring.spring_boot.dto.SaveRenterDTO;
import com.casabonita.spring.spring_boot.entity.Renter;
import com.casabonita.spring.spring_boot.service.RenterService;
import com.casabonita.spring.spring_boot.utils.MappingUtilsRenter;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api")
public class RenterController {

    private final RenterService renterService;
    private final MappingUtilsRenter mappingUtilsRenter;

    public RenterController(RenterService renterService, MappingUtilsRenter mappingUtilsRenter) {
        this.renterService = renterService;
        this.mappingUtilsRenter = mappingUtilsRenter;
    }

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
    public RenterDTO addNewRenter(@RequestBody SaveRenterDTO saveRenterDTO){

        Renter renter = mappingUtilsRenter.mapToRenter(saveRenterDTO);

        renter = renterService.save(renter);

        return mappingUtilsRenter.mapToRenterDTO(renter);
    }

    @PutMapping(value = "/renters/{id}")
    public RenterDTO updateRenter(@RequestBody SaveRenterDTO saveRenterDTO, @PathVariable Integer id){

        Renter renter = mappingUtilsRenter.mapToRenter(saveRenterDTO);

        renter.setId(id);

        renter = renterService.save(renter);

        return mappingUtilsRenter.mapToRenterDTO(renter);
    }

    @DeleteMapping(value = "/renters/{id}")
    public String deleteRenter(@PathVariable Integer id){

        renterService.deleteById(id);

        return "Renter with id = " + id + " was deleted.";
    }
}
