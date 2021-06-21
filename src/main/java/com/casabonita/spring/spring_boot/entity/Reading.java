package com.casabonita.spring.spring_boot.entity;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="meter_data")
public class Reading {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.DETACH,
            CascadeType.REFRESH, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinColumn(name="meter_id")
    private Meter meter;

    @Column(name="data")
    private int transferData;

    @Column(name="data_date")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date transferDate;

    public Reading() {
    }

    public Reading(Meter meter, int transferData, Date transferDate) {
        this.meter = meter;
        this.transferData = transferData;
        this.transferDate = transferDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Meter getMeter() {
        return meter;
    }

    public void setMeter(Meter meter) {
        this.meter = meter;
    }

    public int getTransferData() {
        return transferData;
    }

    public void setTransferData(int transferData) {
        this.transferData = transferData;
    }

    public Date getTransferDate() {
        return transferDate;
    }

    public void setTransferDate(Date transferDate) {
        this.transferDate = transferDate;
    }
}
