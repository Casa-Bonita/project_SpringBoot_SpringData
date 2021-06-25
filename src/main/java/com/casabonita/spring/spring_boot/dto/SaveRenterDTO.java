package com.casabonita.spring.spring_boot.dto;

import java.util.Date;

public class SaveRenterDTO {

    private String name;
    private String ogrn;
    private String inn;
    private Date registrDate;
    private String address;
    private String directorName;
    private String contactName;
    private String phoneNumber;

    public SaveRenterDTO() {
    }

    public SaveRenterDTO(String name, String ogrn, String inn, Date registrDate, String address, String directorName,
                         String contactName, String phoneNumber) {
        this.name = name;
        this.ogrn = ogrn;
        this.inn = inn;
        this.registrDate = registrDate;
        this.address = address;
        this.directorName = directorName;
        this.contactName = contactName;
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOgrn() {
        return ogrn;
    }

    public void setOgrn(String ogrn) {
        this.ogrn = ogrn;
    }

    public String getInn() {
        return inn;
    }

    public void setInn(String inn) {
        this.inn = inn;
    }

    public Date getRegistrDate() {
        return registrDate;
    }

    public void setRegistrDate(Date registrDate) {
        this.registrDate = registrDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDirectorName() {
        return directorName;
    }

    public void setDirectorName(String directorName) {
        this.directorName = directorName;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}