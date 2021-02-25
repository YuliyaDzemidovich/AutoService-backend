package org.example.model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import java.sql.Date;

@Entity
@DiscriminatorValue("ServiceAdvisor")
@PrimaryKeyJoinColumn(name = "employee_id")
public class ServiceAdvisor extends Employee {
    @Column
    private int salary;

    public ServiceAdvisor() {

    }

    public ServiceAdvisor(String surname, String name, String patronage, Date dateOfBirth, Date dateHired, Date dateFired, int salary) {
        super(surname, name, patronage, dateOfBirth, dateHired, dateFired);
        this.salary = salary;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }
}
