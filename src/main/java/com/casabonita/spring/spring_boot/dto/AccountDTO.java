package com.casabonita.spring.spring_boot.dto;

public class AccountDTO {

    private Integer id;
    private String number;
    private ContractDTO accountContractDTO;

    public AccountDTO() {
    }

    public AccountDTO(Integer id, String number, ContractDTO accountContractDTO) {
        this.id = id;
        this.number = number;
        this.accountContractDTO = accountContractDTO;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public ContractDTO getAccountContractDTO() {
        return accountContractDTO;
    }

    public void setAccountContractDTO(ContractDTO accountContractDTO) {
        this.accountContractDTO = accountContractDTO;
    }
}
