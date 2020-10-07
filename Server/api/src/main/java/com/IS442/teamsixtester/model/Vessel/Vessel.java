package com.IS442.teamsixtester.model.Vessel;

import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.UUID;

public class Vessel implements Serializable {

    @Id
    @NotNull
    private UUID vesselId;

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

    public Vessel() {
    }

    public Vessel(@NotNull UUID vesselId, @NotBlank String abbrVslM, String inVoyN,
                  String outVoyN, String bthgDt, String unbthgDt, String berthN,
                  String status, int changeCount, double degreeChange, String firstBerthTime) {
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