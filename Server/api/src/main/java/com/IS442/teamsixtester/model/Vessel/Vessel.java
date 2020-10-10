package com.IS442.teamsixtester.model.Vessel;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.web.bind.annotation.CrossOrigin;
import com.IS442.teamsixtester.model.Account.Account;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "shipping_info")
public class Vessel implements Serializable {

    @Id
    @NotNull
    @Column(name = "vessel_id")
    private UUID vesselId;

    @NotBlank
    @Column(name = "vessel_short_name")
    private String abbrVslM;

    @Column(name = "incoming_voyage_number")
    private String inVoyN;

    @Column(name = "outgoing_voyage_number")
    private String outVoyN;

    @Column(name = "berth_time_required")
    private String bthgDt;

    @Column(name = "expected_datetime_departure")
    private String unbthgDt;

    @Column(name = "berth_number")
    private String berthN;

    @Column(name = "status")
    private String status;

    @Column(name = "change_count")
    private int changeCount;

    @Column(name = "degree_change")
    private double degreeChange;

    @Column(name = "first_berth_time")
    private String firstBerthTime;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "vessels")
    private Set<Account> accounts;

    public Vessel() {
    }

    public Vessel(@NotNull UUID vesselId, @NotBlank String abbrVslM, String inVoyN,
                  String outVoyN, String bthgDt, String unbthgDt, String berthN,
                  String status, int changeCount, double degreeChange,
                  String firstBerthTime, Set<Account> account) {
        this.vesselId = vesselId;
        this.abbrVslM = abbrVslM;
        this.inVoyN = inVoyN;
        this.outVoyN = outVoyN;
        this.bthgDt = bthgDt;
        this.unbthgDt = unbthgDt;
        this.berthN = berthN;
        this.status = status;
        this.changeCount = changeCount;
        this.degreeChange = degreeChange;
        this.firstBerthTime = firstBerthTime;
        this.accounts = accounts;
    }

    public UUID getVesselId() {
        return vesselId;
    }

    public void setVesselId(UUID vesselId) {
        this.vesselId = vesselId;
    }

    public String getAbbrVslM() {
        return abbrVslM;
    }

    public void setAbbrVslM(String abbrVslM) {
        this.abbrVslM = abbrVslM;
    }

    public String getInVoyN() {
        return inVoyN;
    }

    public void setInVoyN(String inVoyN) {
        this.inVoyN = inVoyN;
    }

    public String getOutVoyN() {
        return outVoyN;
    }

    public void setOutVoyN(String outVoyN) {
        this.outVoyN = outVoyN;
    }

    public String getBthgDt() {
        return bthgDt;
    }

    public void setBthgDt(String bthgDt) {
        this.bthgDt = bthgDt;
    }

    public String getUnbthgDt() {
        return unbthgDt;
    }

    public void setUnbthgDt(String unbthgDt) {
        this.unbthgDt = unbthgDt;
    }

    public String getBerthN() {
        return berthN;
    }

    public void setBerthN(String berthN) {
        this.berthN = berthN;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getChangeCount() {
        return changeCount;
    }

    public void setChangeCount(int changeCount) {
        this.changeCount = changeCount;
    }

    public double getDegreeChange() {
        return degreeChange;
    }

    public void setDegreeChange(double degreeChange) {
        this.degreeChange = degreeChange;
    }

    public String getFirstBerthTime() {
        return firstBerthTime;
    }

    public void setFirstBerthTime(String firstBerthTime) {
        this.firstBerthTime = firstBerthTime;
    }

    public Set<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(Set<Account> accounts) {
        this.accounts = accounts;
    }

    @Override
    public String toString() {
        return "Vessel{" +
                "vesselId=" + vesselId +
                ", vesselShortName='" + abbrVslM + '\'' +
                ", incomingVoyageNumber='" + inVoyN + '\'' +
                ", outgoingVoyageNumber='" + outVoyN + '\'' +
                ", berthTimeRequired='" + bthgDt + '\'' +
                ", expectedDatetimeDeparture='" + unbthgDt + '\'' +
                ", berthNumber='" + berthN + '\'' +
                ", status='" + status + '\'' +
                ", changeCount=" + changeCount +
                ", degreeChange=" + degreeChange +
                ", firstBerthTime='" + firstBerthTime + '\'' +
                '}';
    }
}