package org.example.model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import java.sql.Date;

@Entity
@DiscriminatorValue("Mechanic")
@PrimaryKeyJoinColumn(name = "employee_id")
public class Mechanic extends Employee {
    @Column
    private String specialization;
    @Column
    private int workingCategory;

    public Mechanic(){

    }

    public Mechanic(String surname, String name, String patronage, Date dateOfBirth, Date dateHired, Date dateFired, String specialization, int workingCategory) {
        super(surname, name, patronage, dateOfBirth, dateHired, dateFired);
        this.specialization = specialization;
        this.workingCategory = workingCategory;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public int getWorkingCategory() {
        return workingCategory;
    }

    public void setWorkingCategory(int workingCategory) {
        this.workingCategory = workingCategory;
    }
}
