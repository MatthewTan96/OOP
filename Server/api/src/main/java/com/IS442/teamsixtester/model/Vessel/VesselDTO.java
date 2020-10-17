package com.IS442.teamsixtester.model.Vessel;

import com.IS442.teamsixtester.model.Account.Account;
import com.IS442.teamsixtester.model.DTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Set;
import java.util.UUID;

public class VesselDTO implements Serializable, DTO {
    @NotBlank
    private String abbrVslM;

    private String inVoyN;

    private String outVoyN;

    private String bthgDt;

    private String unbthgDt;

    private String berthN;

    private String status;

    private int changeCount;

    private double degreeChange;

    private String firstBerthTime;

    private Set<Account> favouritedByAccounts;

    public VesselDTO() {
    }

    public VesselDTO(String abbrVslM, String inVoyN, String outVoyN, String bthgDt,
                     String unbthgDt, String berthN, String status) {
        this.abbrVslM = abbrVslM;
        this.inVoyN = inVoyN;
        this.outVoyN = outVoyN;
        this.bthgDt = bthgDt;
        this.unbthgDt = unbthgDt;
        this.berthN = berthN;
        this.status = status;
        this.changeCount = 0;
        this.degreeChange = 0;
        this.favouritedByAccounts = null;
    }

//    public VesselDTO(String abbrVslM, String inVoyN, String outVoyN, String bthgDt, String unbthgDt, String berthN,
//                     String status, int changeCount, double degreeChange, String firstBerthTime) {
//        this.abbrVslM = abbrVslM;
//        this.inVoyN = inVoyN;
//        this.outVoyN = outVoyN;
//        this.bthgDt = bthgDt;
//        this.unbthgDt = unbthgDt;
//        this.berthN = berthN;
//        this.status = status;
//        this.changeCount = changeCount;
//        this.degreeChange = degreeChange;
//        this.firstBerthTime = firstBerthTime;
//    }

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

    @Override
    public String toString() {
        return "VesselDTO{" +
                "abbrVslM='" + abbrVslM + '\'' +
                ", inVoyN='" + inVoyN + '\'' +
                ", outVoyN='" + outVoyN + '\'' +
                ", bthgDt='" + bthgDt + '\'' +
                ", unbthgDt='" + unbthgDt + '\'' +
                ", berthN='" + berthN + '\'' +
                ", status='" + status + '\'' +
                ", changeCount=" + changeCount +
                ", degreeChange=" + degreeChange +
                ", firstBerthTime='" + firstBerthTime + '\'' +
                '}';
    }

    @Override
    public Vessel toTrueClass() {
        firstBerthTime = this.getBthgDt();
        return new Vessel(UUID.randomUUID(),
                this.abbrVslM, this.inVoyN, this.outVoyN, this.bthgDt, this.unbthgDt, this.berthN, this.status,
                this.changeCount, this.degreeChange, this.firstBerthTime, this.favouritedByAccounts);
    }
}
