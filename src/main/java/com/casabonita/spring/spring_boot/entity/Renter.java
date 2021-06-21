package com.casabonita.spring.spring_boot.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="renter")
public class Renter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @Column(name="name")
    private String name;

    @Column(name="ogrn")
    private String ogrn;

    @Column(name="inn")
    private String inn;

    @Column(name="registr_date")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date registrDate;

    @Column(name="address")
    private String address;

    @Column(name="director_name")
    private String directorName;

    @Column(name="contact_name")
    private String contactName;

    @Column(name="phone")
    private String phoneNumber;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "renter")
    @JsonIgnore
    private List<Contract> contractList;

    public Renter() {
    }

    public Renter(String name, String ogrn, String inn, Date registrDate, String address, String directorName,
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public List<Contract> getContractList() {
        return contractList;
    }

    public void setContractList(List<Contract> contractList) {
        this.contractList = contractList;
    }
}
