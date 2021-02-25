package org.example.model;

import javax.persistence.*;

/**
 * This class represents status of each fault found in one particular {@link Order}.
 * Each fault found has order it belongs. Fault also may have a diagnostics made, but that's not necessary.
 * Each fault has status set, depending whether it was found during diagnostics or claimed initially by client,
 * whether it needs to be fixed or not (client may refuse fixing it), whether it was fixed or not. There is
 * also fault status, when the fault was claimed by client, but wasn't found.
 * @see model.FaultStatus
 */
@Entity
public class Fault {
    @Id
    @GeneratedValue
    private long id;
    @ManyToOne
    @JoinColumn(name = "faulttype", nullable = false)
    private FaultType faultType;
    @OneToOne
    @JoinColumn(name = "diagnostics")
    private Diagnostics diagnostics;
    @Enumerated(EnumType.STRING)
    private FaultStatus faultStatus;

    public Fault() {

    }

    public Fault(FaultType fault, Diagnostics diagnostics, FaultStatus faultStatus) {
        this.faultType = fault;
        this.diagnostics = diagnostics;
        this.faultStatus = faultStatus;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public FaultType getFaultType() {
        return faultType;
    }

    public void setFaultFk(FaultType fault) {
        this.faultType = fault;
    }

    public Diagnostics getDiagnostics() {
        return diagnostics;
    }

    public void setDiagnostics(Diagnostics diagnostics) {
        this.diagnostics = diagnostics;
    }

    public FaultStatus getFaultStatus() {
        return faultStatus;
    }

    public void setFaultStatus(FaultStatus faultStatus) {
        this.faultStatus = faultStatus;
    }
}
