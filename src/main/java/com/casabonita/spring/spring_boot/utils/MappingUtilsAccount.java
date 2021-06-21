package com.casabonita.spring.spring_boot.utils;

import com.casabonita.spring.spring_boot.dto.AccountDTO;
import com.casabonita.spring.spring_boot.entity.Account;
import org.springframework.stereotype.Component;

@Component
public class MappingUtilsAccount {

    private final MappingUtilsContract mappingUtilsContract;

    public MappingUtilsAccount(MappingUtilsContract mappingUtilsContract) {
        this.mappingUtilsContract = mappingUtilsContract;
    }


    public AccountDTO mapToAccountDTO(Account account){
        AccountDTO dto = new AccountDTO();
        dto.setId(account.getId());
        dto.setNumber(account.getNumber());
        dto.setAccountContractDTO(mappingUtilsContract.mapToContractDTO(account.getAccountContract()));

        return dto;
    }

    //из dto в entity
    public Account mapToAccount(AccountDTO dto){
        Account account = new Account();
        account.setId(dto.getId());
        account.setNumber(dto.getNumber());
        account.setAccountContract(mappingUtilsContract.mapToContract(dto.getAccountContractDTO()));

        return account;
    }
}