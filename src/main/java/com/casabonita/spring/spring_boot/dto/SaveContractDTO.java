package com.casabonita.spring.spring_boot.dto;

import java.util.Date;

public class SaveContractDTO {

    private String number;
    private Date date;
    private int fare;
    private Date startDate;
    private Date finishDate;
    private int paymentDay;
    private int placeNumber;
    private String renterName;

    public SaveContractDTO() {
    }

    public SaveContractDTO(String number, Date date, int fare, Date startDate, Date finishDate, int paymentDay, int placeNumber, String renterName) {
        this.number = number;
        this.date = date;
        this.fare = fare;
        this.startDate = startDate;
        this.finishDate = finishDate;
        this.paymentDay = paymentDay;
        this.placeNumber = placeNumber;
        this.renterName = renterName;
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

    public int getPlaceNumber() {
        return placeNumber;
    }

    public void setPlaceNumber(int placeNumber) {
        this.placeNumber = placeNumber;
    }

    public String getRenterName() {
        return renterName;
    }

    public void setRenterName(String renterName) {
        this.renterName = renterName;
    }
}