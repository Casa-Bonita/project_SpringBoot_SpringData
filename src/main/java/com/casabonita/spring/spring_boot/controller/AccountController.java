package com.casabonita.spring.spring_boot.controller;

import com.casabonita.spring.spring_boot.dto.AccountDTO;
import com.casabonita.spring.spring_boot.entity.Account;
import com.casabonita.spring.spring_boot.service.AccountService;
import com.casabonita.spring.spring_boot.utils.MappingUtilsAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private MappingUtilsAccount mappingUtilsAccount;

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
    public Account addNewAccount(@RequestBody Account account, @RequestParam String accountContractNumber){

        accountService.save(account, accountContractNumber);

        return account;
    }

    @PutMapping(value = "/accounts")
    public Account updateAccount(@RequestBody Account account, @RequestParam String accountContractNumber){

        accountService.save(account, accountContractNumber);

        return account;
    }

    @DeleteMapping(value = "/accounts/{id}")
    public String deleteAccount(@PathVariable Integer id){

        Account account = accountService.findById(id);

        if(account == null){
            return "There is no Account with id = " + id + " in DataBase.";
        }

        accountService.deleteById(id);

        return "Account with id = " + id + " was deleted.";
    }

}
