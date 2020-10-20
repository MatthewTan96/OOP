package com.IS442.teamsixtester.model.Vessel;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.IS442.teamsixtester.model.Account.Account;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
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

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "favouritedVessels")
    private Set<Account> favouritedByAccounts;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "subscribedVessels")
    private Set<Account> subscribedByAccounts;

    public Vessel() {
    }

    public Vessel(@NotNull UUID vesselId, @NotBlank String abbrVslM, String inVoyN,
                  String outVoyN, String bthgDt, String unbthgDt, String berthN,
                  String status) {
        this.vesselId = vesselId;
        this.abbrVslM = abbrVslM;
        this.inVoyN = inVoyN;
        this.outVoyN = outVoyN;
        this.bthgDt = bthgDt;
        this.unbthgDt = unbthgDt;
        this.berthN = berthN;
        this.status = status;
        this.changeCount = 0;
        this.degreeChange = 0;
        this.firstBerthTime = bthgDt;
        this.favouritedByAccounts = null;
        this.subscribedByAccounts = null;
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

    public Set<Account> getFavouritedByAccounts() {
        return favouritedByAccounts;
    }

    public void setFavouritedByAccounts(Set<Account> favouritedByAccounts) {
        this.favouritedByAccounts = favouritedByAccounts;
    }

    // to associate favourited account to this vessel
    public void addFavouritedByAccount(Account account) {
        this.favouritedByAccounts.add(account);
        Set<Vessel> retrievedVessels = account.getFavouritedVessels();
        retrievedVessels.add(this);
        account.setFavouritedVessels(retrievedVessels);
    }

    //  to disassociate favourited account from this vessel
    public void removeFavouritedByAccount(Account account) {
        this.favouritedByAccounts.remove(account);
        Set<Vessel> retrievedVessels = account.getFavouritedVessels();
        retrievedVessels.remove(this);
        account.setFavouritedVessels(retrievedVessels);
    }

    // disassociate all favourited accounts from this vessel, used when deleting the vessel
    public void removeAllFavouritedByAccounts() {
        for (Account account : new ArrayList<>(favouritedByAccounts)) {
            removeFavouritedByAccount(account);
        }
    }

    public Set<Account> getSubscribedByAccounts() {
        return subscribedByAccounts;
    }

    public void setSubscribedByAccounts(Set<Account> subscribedByAccounts) {
        this.subscribedByAccounts = subscribedByAccounts;
    }

    // to associate subscribed account to this vessel
    public void addSubscribedByAccount(Account account) {
        this.subscribedByAccounts.add(account);
        Set<Vessel> retrievedVessels = account.getSubscribedVessels();
        retrievedVessels.add(this);
        account.setSubscribedVessels(retrievedVessels);
    }

    //  to disassociate subscribed account from this vessel
    public void removeSubscribedByAccount(Account account) {
        this.subscribedByAccounts.remove(account);
        Set<Vessel> retrievedVessels = account.getSubscribedVessels();
        retrievedVessels.remove(this);
        account.setSubscribedVessels(retrievedVessels);
    }

    // disassociate all subscribed accounts from this vessel, used when deleting the vessel
    public void removeAllSubscribedByAccounts() {
        for (Account account : new ArrayList<>(subscribedByAccounts)) {
            removeSubscribedByAccount(account);
        }
    }

    @Override
    public String toString() {
        return "Vessel{" +
                "vesselId=" + vesselId +
                ", abbrVslM='" + abbrVslM + '\'' +
                ", inVoyN='" + inVoyN + '\'' +
                ", outVoyN='" + outVoyN + '\'' +
                ", bthgDt='" + bthgDt + '\'' +
                ", unbthgDt='" + unbthgDt + '\'' +
                ", berthN='" + berthN + '\'' +
                ", status='" + status + '\'' +
                ", changeCount=" + changeCount +
                ", degreeChange=" + degreeChange +
                ", firstBerthTime='" + firstBerthTime + '\'' +
                ", favouritedByAccounts=" + favouritedByAccounts +
                ", subscribedByAccounts=" + subscribedByAccounts +
                '}';
    }
}