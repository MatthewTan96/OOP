package com.IS442.teamsixtester.domain;


import javax.persistence.*;

@Entity
@Table(name="shipping_info")
public class Vessel {
    @Id
    @GeneratedValue
    @Column(name="vessel_id")
    private Long id;

    @Column(name="vessel_short_name")
    private String vesselShortName;

    @Column(name="incoming_voyage_number")
    private String incomingVoyageNumber;

    @Column(name="outgoing_voyage_number")
    private String outgoingVoyageNumber;

    @Column(name="berth_time_required")
    private String berthTimeRequired;

    @Column(name="expected_datetime_departure")
    private String expectedDateTimeDeparture;

    @Column(name="berth_number")
    private String berthNumber;

    @Column(name="status")
    private String status;

    @Column(name="change_count")
    private int changeCount;

    @Column(name="degree_change")
    private long degreeChange;

    @Column(name="first_berth_time")
    private final String firstBerthTime;

    public Vessel(Long id, String vesselShortName, String incomingVoyageNumber, String outgoingVoyageNumber,
                  String berthTimeRequired, String expectedDateTimeDeparture, String berthNumber, String status) {
        this.id = id;
        this.vesselShortName = vesselShortName;
        this.incomingVoyageNumber = incomingVoyageNumber;
        this.outgoingVoyageNumber = outgoingVoyageNumber;
        this.berthTimeRequired = berthTimeRequired;
        this.expectedDateTimeDeparture = expectedDateTimeDeparture;
        this.berthNumber = berthNumber;
        this.status = status;
        this.changeCount = 0;
        this.degreeChange = 0;
        this.firstBerthTime = berthTimeRequired;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getExpectedDateTimeDeparture() {
        return expectedDateTimeDeparture;
    }

    public void setExpectedDateTimeDeparture(String expectedDateTimeDeparture) {
        this.expectedDateTimeDeparture = expectedDateTimeDeparture;
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

    public long getDegreeChange() {
        return degreeChange;
    }

    public void setDegreeChange(long degreeChange) {
        this.degreeChange = degreeChange;
    }

    public String getFirstBerthTime() {
        return firstBerthTime;
    }

    @Override
    public String toString() {
        return "Vessel{" +
                "id=" + id +
                ", vesselShortName='" + vesselShortName + '\'' +
                ", incomingVoyageNumber='" + incomingVoyageNumber + '\'' +
                ", outgoingVoyageNumber='" + outgoingVoyageNumber + '\'' +
                ", berthTimeRequired='" + berthTimeRequired + '\'' +
                ", expectedDateTimeDeparture='" + expectedDateTimeDeparture + '\'' +
                ", berthNumber='" + berthNumber + '\'' +
                ", status='" + status + '\'' +
                ", changeCount=" + changeCount +
                ", degreeChange=" + degreeChange +
                ", firstBerthTime='" + firstBerthTime + '\'' +
                '}';
    }
}