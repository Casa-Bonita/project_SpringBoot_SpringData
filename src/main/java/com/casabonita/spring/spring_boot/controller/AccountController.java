package com.casabonita.spring.spring_boot.controller;

import com.casabonita.spring.spring_boot.dto.AccountDTO;
import com.casabonita.spring.spring_boot.dto.SaveAccountDTO;
import com.casabonita.spring.spring_boot.entity.Account;
import com.casabonita.spring.spring_boot.service.AccountService;
import com.casabonita.spring.spring_boot.utils.MappingUtilsAccount;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api")
public class AccountController {

    private final AccountService accountService;
    private final MappingUtilsAccount mappingUtilsAccount;

    public AccountController(AccountService accountService, MappingUtilsAccount mappingUtilsAccount) {
        this.accountService = accountService;
        this.mappingUtilsAccount = mappingUtilsAccount;
    }

    @GetMapping (value = "/accounts")
    public List<AccountDTO> showAllAccounts(){

        List<Account> allAccounts = accountService.findAll();

        return allAccounts.stream()
                .map(mappingUtilsAccount::mapToAccountDTO)
                .collect(Collectors.toList());
    }

    @GetMapping(value = "/accounts/{id}")
    public AccountDTO getAccount(@PathVariable Integer id){

        return mappingUtilsAccount.mapToAccountDTO(accountService.findById(id));
    }

    @PostMapping(value = "/accounts")
    public AccountDTO addNewAccount(@RequestBody SaveAccountDTO saveAccountDTO){

        Account account = mappingUtilsAccount.mapToAccount(saveAccountDTO);

        account = accountService.save(account, saveAccountDTO.getAccountContractDTO().getNumber());

        return mappingUtilsAccount.mapToAccountDTO(account);
    }

    @PutMapping(value = "/accounts/{id}")
    public AccountDTO updateAccount(@RequestBody SaveAccountDTO saveAccountDTO, @PathVariable Integer id){

        Account account = mappingUtilsAccount.mapToAccount(saveAccountDTO);

        account.setId(id);

        account = accountService.save(account, saveAccountDTO.getAccountContractDTO().getNumber());

        return mappingUtilsAccount.mapToAccountDTO(account);
    }

    @DeleteMapping(value = "/accounts/{id}")
    public String deleteAccount(@PathVariable Integer id){

        accountService.deleteById(id);

        return "Account with id = " + id + " was deleted.";
    }
}
