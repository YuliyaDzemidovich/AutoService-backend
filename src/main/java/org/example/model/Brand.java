package org.example.model;

import javax.persistence.*;

@Entity
public class Brand {
    @Id
    @GeneratedValue
    private long id;
    @Column(unique = true)
    private String name;
    @ManyToOne
    @JoinColumn(name = "country")
    private Country country;

    public Brand() {

    }

    public Brand(String name, Country country) {
        this.name = name;
        this.country = country;
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

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }
}
