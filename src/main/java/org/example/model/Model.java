package org.example.model;

import javax.persistence.*;

@Entity
public class Model {
    @Id
    @GeneratedValue
    private long id;
    @Column(unique = true)
    private String name;
    @ManyToOne
    @JoinColumn(name = "brand")
    private Brand brand;

    public Model() {

    }

    public Model(String name, Brand brand) {
        this.name = name;
        this.brand = brand;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }
}
