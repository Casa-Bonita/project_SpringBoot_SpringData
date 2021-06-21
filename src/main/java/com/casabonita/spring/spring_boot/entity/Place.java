package com.casabonita.spring.spring_boot.entity;

import javax.persistence.*;

@Entity
@Table(name="place")
public class Place {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @Column(name="number")
    private int number;

    @Column(name="name")
    private String name;

    @Column(name="square")
    private double square;

    @Column(name="floor")
    private int floor;

    @Column(name="type")
    private String type;

    @OneToOne(mappedBy="contractPlace", cascade = CascadeType.ALL)
    private Contract contract;

    @OneToOne(mappedBy="meterPlace", cascade = CascadeType.ALL)
    private Meter meter;

    public Place() {
    }

    public Place(int number, String name, double square, int floor, String type, Contract contract, Meter meter) {
        this.number = number;
        this.name = name;
        this.square = square;
        this.floor = floor;
        this.type = type;
        this.contract = contract;
        this.meter = meter;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getSquare() {
        return square;
    }

    public void setSquare(double square) {
        this.square = square;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Contract getContract() {
        return contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }

    public Meter getMeter() {
        return meter;
    }

    public void setMeter(Meter meter) {
        this.meter = meter;
    }
}
