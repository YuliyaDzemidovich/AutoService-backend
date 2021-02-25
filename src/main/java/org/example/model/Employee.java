package org.example.model;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(discriminatorType = DiscriminatorType.STRING, name = "type")
public class Employee {
    @Id
    @GeneratedValue (generator = "sequence-generator")
    @GenericGenerator(
            name = "sequence-generator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @Parameter(name = "sequence_name", value = "sequence_employee_id"),
                    @Parameter(name = "initial_value", value = "1"),
                    @Parameter(name = "increment_size", value = "1")
            }
    )
    private long id;
    @Column
    private String surname;
    @Column
    private String name;
    @Column
    private String patronage;
    @Column
    private Date dateOfBirth;
    @Column
    private Date dateHired;
    @Column
    private Date dateFired;

    public Employee() {

    }

    public Employee(String surname, String name, String patronage, Date dateOfBirth, Date dateHired, Date dateFired) {
        this.surname = surname;
        this.name = name;
        this.patronage = patronage;
        this.dateOfBirth = dateOfBirth;
        this.dateHired = dateHired;
        this.dateFired = dateFired;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Date getDateHired() {
        return dateHired;
    }

    public void setDateHired(Date dateHired) {
        this.dateHired = dateHired;
    }

    public Date getDateFired() {
        return dateFired;
    }

    public void setDateFired(Date dateFired) {
        this.dateFired = dateFired;
    }
}
