package com.casabonita.spring.spring_boot.entity;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="contract")
public class Contract {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @Column(name="number")
    private String number;

    @Column(name="contract_date")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date date;

    @Column(name="fare")
    private int fare;

    @Column(name="start_date")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date startDate;

    @Column(name="finish_date")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date finishDate;

    @Column(name="payment_day")
    private int paymentDay;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.REFRESH, CascadeType.MERGE})
    @JoinColumn(name="place_id")
    private Place contractPlace;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.DETACH,
            CascadeType.REFRESH, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinColumn(name="renter_id")
    private Renter renter;

    @OneToOne(mappedBy="accountContract", cascade = CascadeType.ALL)
    private Account account;

    public Contract() {
    }

    public Contract(String number, Date date, int fare, Date startDate, Date finishDate, int paymentDay,
                    Place contractPlace, Renter renter, Account account) {
        this.number = number;
        this.date = date;
        this.fare = fare;
        this.startDate = startDate;
        this.finishDate = finishDate;
        this.paymentDay = paymentDay;
        this.contractPlace = contractPlace;
        this.renter = renter;
        this.account = account;
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

    public Place getContractPlace() {
        return contractPlace;
    }

    public void setContractPlace(Place contractPlace) {
        this.contractPlace = contractPlace;
    }

    public Renter getRenter() {
        return renter;
    }

    public void setRenter(Renter renter) {
        this.renter = renter;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
