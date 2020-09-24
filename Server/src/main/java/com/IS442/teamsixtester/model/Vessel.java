package com.IS442.teamsixtester.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import java.util.UUID;

public class Vessel {

    private UUID vesselId;
    @NotBlank
    private String abbrVslM;

    private String inVoyN;

    private String outVoyN;

    private String btrDt;

    private String etdDt;

    private String berthN;

    private String status;

    private int changeCount;

    private double degreeChange;

    private String firstBerthTime;

    public Vessel() {
    }

    public Vessel(UUID vesselId, @JsonProperty("abbrVslM") String abbrVslM,
                  @JsonProperty("inVoyN") String inVoyN,
                  @JsonProperty("outVoyN") String outVoyN,
                  @JsonProperty("btrDt") String btrDt,
                  @JsonProperty("etdDt") String etdDt,
                  @JsonProperty("berthN") String berthN,
                  @JsonProperty("status") String status) {
        this.vesselId = vesselId;
        this.abbrVslM = abbrVslM;
        this.inVoyN = inVoyN;
        this.outVoyN = outVoyN;
        this.btrDt = btrDt;
        this.etdDt = etdDt;
        this.berthN = berthN;
        this.status = status;
        this.changeCount = 0;
        this.degreeChange = 0;
        this.firstBerthTime = btrDt;
    }

    public Vessel(UUID vesselId, @JsonProperty("abbrVslM") String abbrVslM,
                  @JsonProperty("inVoyN")String inVoyN,
                  @JsonProperty("outVoyN") String outVoyN,
                  @JsonProperty("btrDt") String btrDt,
                  @JsonProperty("etdDt") String etdDt,
                  @JsonProperty("berthN") String berthN,
                  @JsonProperty("status") String status,
                  int changeCount, double degreeChange, String firstBerthTime) {
        this.vesselId = vesselId;
        this.abbrVslM = abbrVslM;
        this.inVoyN = inVoyN;
        this.outVoyN = outVoyN;
        this.btrDt = btrDt;
        this.etdDt = etdDt;
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

    public String getBtrDt() {
        return btrDt;
    }

    public void setBtrDt(String btrDt) {
        this.btrDt = btrDt;
    }

    public String getEtdDt() {
        return etdDt;
    }

    public void setEtdDt(String etdDt) {
        this.etdDt = etdDt;
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
                ", berthTimeRequired='" + btrDt + '\'' +
                ", expectedDatetimeDeparture='" + etdDt + '\'' +
                ", berthNumber='" + berthN + '\'' +
                ", status='" + status + '\'' +
                ", changeCount=" + changeCount +
                ", degreeChange=" + degreeChange +
                ", firstBerthTime='" + firstBerthTime + '\'' +
                '}';
    }
}