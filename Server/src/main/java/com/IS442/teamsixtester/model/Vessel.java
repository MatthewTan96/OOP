package com.IS442.teamsixtester.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import java.util.UUID;

public class Vessel {

    private UUID vesselId;
    @NotBlank
    private String vesselShortName;

    private String incomingVoyageNumber;

    private String outgoingVoyageNumber;

    private String berthTimeRequired;

    private String expectedDatetimeDeparture;

    private String berthNumber;

    private String status;

    private int changeCount;

    private double degreeChange;

    private String firstBerthTime;

    public Vessel() {
    }

    public Vessel(UUID vesselId, @JsonProperty("vesselShortName") String vesselShortName,
                  @JsonProperty("incomingVoyageNumber") String incomingVoyageNumber,
                  @JsonProperty("outgoingVoyageNumber") String outgoingVoyageNumber,
                  @JsonProperty("berthTimeRequired") String berthTimeRequired,
                  @JsonProperty("expectedDatetimeDeparture") String expectedDatetimeDeparture,
                  @JsonProperty("berthNumber") String berthNumber,
                  @JsonProperty("status") String status) {
        this.vesselId = vesselId;
        this.vesselShortName = vesselShortName;
        this.incomingVoyageNumber = incomingVoyageNumber;
        this.outgoingVoyageNumber = outgoingVoyageNumber;
        this.berthTimeRequired = berthTimeRequired;
        this.expectedDatetimeDeparture = expectedDatetimeDeparture;
        this.berthNumber = berthNumber;
        this.status = status;
        this.changeCount = 0;
        this.degreeChange = 0;
        this.firstBerthTime = berthTimeRequired;
    }

    public Vessel(UUID vesselId, @JsonProperty("vesselShortName") String vesselShortName,
                  @JsonProperty("incomingVoyageNumber")String incomingVoyageNumber,
                  @JsonProperty("outgoingVoyageNumber") String outgoingVoyageNumber,
                  @JsonProperty("berthTimeRequired") String berthTimeRequired,
                  @JsonProperty("expectedDatetimeDeparture") String expectedDatetimeDeparture,
                  @JsonProperty("berthNumber") String berthNumber,
                  @JsonProperty("status") String status,
                  int changeCount, double degreeChange, String firstBerthTime) {
        this.vesselId = vesselId;
        this.vesselShortName = vesselShortName;
        this.incomingVoyageNumber = incomingVoyageNumber;
        this.outgoingVoyageNumber = outgoingVoyageNumber;
        this.berthTimeRequired = berthTimeRequired;
        this.expectedDatetimeDeparture = expectedDatetimeDeparture;
        this.berthNumber = berthNumber;
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

    public String getVesselShortName() {
        return vesselShortName;
    }

    public void setVesselShortName(String vesselShortName) {
        this.vesselShortName = vesselShortName;
    }

    public String getIncomingVoyageNumber() {
        return incomingVoyageNumber;
    }

    public void setIncomingVoyageNumber(String incomingVoyageNumber) {
        this.incomingVoyageNumber = incomingVoyageNumber;
    }

    public String getOutgoingVoyageNumber() {
        return outgoingVoyageNumber;
    }

    public void setOutgoingVoyageNumber(String outgoingVoyageNumber) {
        this.outgoingVoyageNumber = outgoingVoyageNumber;
    }

    public String getBerthTimeRequired() {
        return berthTimeRequired;
    }

    public void setBerthTimeRequired(String berthTimeRequired) {
        this.berthTimeRequired = berthTimeRequired;
    }

    public String getExpectedDatetimeDeparture() {
        return expectedDatetimeDeparture;
    }

    public void setExpectedDatetimeDeparture(String expectedDatetimeDeparture) {
        this.expectedDatetimeDeparture = expectedDatetimeDeparture;
    }

    public String getBerthNumber() {
        return berthNumber;
    }

    public void setBerthNumber(String berthNumber) {
        this.berthNumber = berthNumber;
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
                ", vesselShortName='" + vesselShortName + '\'' +
                ", incomingVoyageNumber='" + incomingVoyageNumber + '\'' +
                ", outgoingVoyageNumber='" + outgoingVoyageNumber + '\'' +
                ", berthTimeRequired='" + berthTimeRequired + '\'' +
                ", expectedDatetimeDeparture='" + expectedDatetimeDeparture + '\'' +
                ", berthNumber='" + berthNumber + '\'' +
                ", status='" + status + '\'' +
                ", changeCount=" + changeCount +
                ", degreeChange=" + degreeChange +
                ", firstBerthTime='" + firstBerthTime + '\'' +
                '}';
    }
}