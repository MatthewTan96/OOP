package com.IS442.teamsixtester.domain;


import javax.persistence.*;

@Entity
@Table(name="shipping_info")
public class Vessel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="vessel_id")
    private Integer vessel_id;

    @Column(name="vessel_short_name")
    private String vessel_short_name;

    @Column(name="incoming_voyage_number")
    private String incoming_voyage_number;

    @Column(name="outgoing_voyage_number")
    private String outgoing_voyage_number;

    @Column(name="berth_time_required")
    private String berth_time_required;

    @Column(name="expected_datetime_departure")
    private String expected_datetime_departure;

    @Column(name="berth_number")
    private String berth_number;

    @Column(name="status")
    private String status;

    @Column(name="change_count")
    private int change_count;

    @Column(name="degree_change")
    private long degree_change;

    @Column(name="first_berth_time")
    private String first_berth_time;

    public Vessel() {
    }

    public Vessel(String vessel_short_name, String incoming_voyage_number, String outgoing_voyage_number,
                  String berth_time_required, String expected_datetime_departure, String berth_number, String status) {
        this.vessel_short_name = vessel_short_name;
        this.incoming_voyage_number = incoming_voyage_number;
        this.outgoing_voyage_number = outgoing_voyage_number;
        this.berth_time_required = berth_time_required;
        this.expected_datetime_departure = expected_datetime_departure;
        this.berth_number = berth_number;
        this.status = status;
        this.change_count = 0;
        this.degree_change = 0;
        this.first_berth_time = berth_time_required;
    }

    public Integer getVessel_id() {
        return vessel_id;
    }

    public String getVessel_short_name() {
        return vessel_short_name;
    }

    public String getIncoming_voyage_number() {
        return incoming_voyage_number;
    }

    public String getOutgoing_voyage_number() {
        return outgoing_voyage_number;
    }

    public String getBerth_time_required() {
        return berth_time_required;
    }

    public String getExpected_datetime_departure() {
        return expected_datetime_departure;
    }

    public String getBerth_number() {
        return berth_number;
    }

    public String getStatus() {
        return status;
    }

    public int getChange_count() {
        return change_count;
    }

    public long getDegree_change() {
        return degree_change;
    }

    public String getFirst_berth_time() {
        return first_berth_time;
    }

    public void setVessel_short_name(String vessel_short_name) {
        this.vessel_short_name = vessel_short_name;
    }

    public void setIncoming_voyage_number(String incoming_voyage_number) {
        this.incoming_voyage_number = incoming_voyage_number;
    }

    public void setOutgoing_voyage_number(String outgoing_voyage_number) {
        this.outgoing_voyage_number = outgoing_voyage_number;
    }

    public void setBerth_time_required(String berth_time_required) {
        this.berth_time_required = berth_time_required;
    }

    public void setExpected_datetime_departure(String expected_datetime_departure) {
        this.expected_datetime_departure = expected_datetime_departure;
    }

    public void setBerth_number(String berth_number) {
        this.berth_number = berth_number;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setChange_count(int change_count) {
        this.change_count = change_count;
    }

    public void setDegree_change(long degree_change) {
        this.degree_change = degree_change;
    }

    @Override
    public String toString() {
        return "Vessel{" +
                "vessel_id=" + vessel_id +
                ", vessel_short_name='" + vessel_short_name + '\'' +
                ", incoming_voyage_number='" + incoming_voyage_number + '\'' +
                ", outgoing_voyage_number='" + outgoing_voyage_number + '\'' +
                ", berth_time_required='" + berth_time_required + '\'' +
                ", expected_datetime_departure='" + expected_datetime_departure + '\'' +
                ", berth_number='" + berth_number + '\'' +
                ", status='" + status + '\'' +
                ", change_count=" + change_count +
                ", degree_change=" + degree_change +
                ", first_berth_time='" + first_berth_time + '\'' +
                '}';
    }
}