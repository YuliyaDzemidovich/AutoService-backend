package org.example.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Class represents diagnostics in case it was made.
 */
@Entity
public class Diagnostics {
    @Id
    @GeneratedValue
    private long id;
    /**
     * Represents diagnostics paper list stored at car service,
     * which contains found faults or confirms found none.
     */
    @Column (unique = true, nullable = false)
    private int diagnosticsListNumber;

    public Diagnostics() {

    }

    public Diagnostics(int diagnosticsListNumber) {
        this.diagnosticsListNumber = diagnosticsListNumber;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getDiagnosticsListNumber() {
        return diagnosticsListNumber;
    }

    public void setDiagnosticsListNumber(int diagnosticsListNumber) {
        this.diagnosticsListNumber = diagnosticsListNumber;
    }
}
