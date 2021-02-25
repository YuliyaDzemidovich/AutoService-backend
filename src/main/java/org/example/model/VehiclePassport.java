package org.example.model;

import javax.persistence.*;
import java.sql.Date;

@Entity
public class VehiclePassport {
    @Id
    @GeneratedValue
    private long id;
    @ManyToOne
    @JoinColumn(name = "vehicle", nullable = false)
    private Vehicle vehicle;
    @Column
    private String plateNumber;
    @ManyToOne
    @JoinColumn(name = "client", nullable = false)
    private Client owner;
    @Column
    private Date registrationDate;
    @ManyToOne
    @JoinColumn(name = "country")
    private Country country;
    @Column
    private boolean status;

    public VehiclePassport() {
    }

    public VehiclePassport(Vehicle vehicle, String plateNumber, Client owner, Date registrationDate, Country country, boolean status) {
        this.vehicle = vehicle;
        this.plateNumber = plateNumber;
        this.owner = owner;
        this.registrationDate = registrationDate;
        this.country = country;
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public Client getOwner() {
        return owner;
    }

    public void setOwner(Client owner) {
        this.owner = owner;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
