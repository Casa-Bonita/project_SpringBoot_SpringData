package com.casabonita.spring.spring_boot.dto;

import java.util.Date;

public class ContractDTO {

    private Integer id;
    private String number;
    private Date date;
    private int fare;
    private Date startDate;
    private Date finishDate;
    private int paymentDay;
    private PlaceDTO contractPlaceDTO;
    private RenterDTO renterDTO;

    public ContractDTO() {
    }

    public ContractDTO(Integer id, String number, Date date, int fare, Date startDate, Date finishDate, int paymentDay,
                       PlaceDTO contractPlaceDTO, RenterDTO renterDTO) {
        this.id = id;
        this.number = number;
        this.date = date;
        this.fare = fare;
        this.startDate = startDate;
        this.finishDate = finishDate;
        this.paymentDay = paymentDay;
        this.contractPlaceDTO = contractPlaceDTO;
        this.renterDTO = renterDTO;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getFare() {
        return fare;
    }

    public void setFare(int fare) {
        this.fare = fare;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

    public int getPaymentDay() {
        return paymentDay;
    }

    public void setPaymentDay(int paymentDay) {
        this.paymentDay = paymentDay;
    }

    public PlaceDTO getContractPlaceDTO() {
        return contractPlaceDTO;
    }

    public void setContractPlaceDTO(PlaceDTO contractPlaceDTO) {
        this.contractPlaceDTO = contractPlaceDTO;
    }

    public RenterDTO getRenterDTO() {
        return renterDTO;
    }

    public void setRenterDTO(RenterDTO renterDTO) {
        this.renterDTO = renterDTO;
    }
}
