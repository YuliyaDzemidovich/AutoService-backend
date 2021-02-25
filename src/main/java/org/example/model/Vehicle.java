package org.example.model;

import javax.persistence.*;

@Entity
public class Vehicle {
    @Id
    @GeneratedValue
    private long id;
    @Column(unique = true)
    private String vin;
    @Column
    private int dateOfProduction;
    @Column
    private String color;
    @ManyToOne
    @JoinColumn(name = "model")
    private Model model;

    public Vehicle() {

    }

    public Vehicle(String vin, int dateOfProduction, String color, Model model) {
        this.vin = vin;
        this.dateOfProduction = dateOfProduction;
        this.color = color;
        this.model = model;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public int getDateOfProduction() {
        return dateOfProduction;
    }

    public void setDateOfProduction(int dateOfProduction) {
        this.dateOfProduction = dateOfProduction;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }
}
