package org.example.model;

import javax.persistence.*;
import java.sql.Date;

@Entity
@DiscriminatorValue("Individual")
@PrimaryKeyJoinColumn(name = "client_id")
public class IndividualClient extends Client{
    @Column
    private String surname;
    @Column
    private String name;
    @Column
    private String patronage;
    @Column
    private String address;
    @Column (unique = true)
    private String phone;
    @Column
    private Date dateOfBirth;

    public IndividualClient() {

    }

    public IndividualClient(String surname, String name, String patronage, String address, String phone, Date dateOfBirth) {
        this.surname = surname;
        this.name = name;
        this.patronage = patronage;
        this.address = address;
        this.phone = phone;
        this.dateOfBirth = dateOfBirth;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPatronage() {
        return patronage;
    }

    public void setPatronage(String patronage) {
        this.patronage = patronage;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
}
