package com.IS442.teamsixtester.model.Account;

import com.IS442.teamsixtester.model.Vessel.Vessel;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Set;

@Entity
@Table(name = "login_info")
public class Account {
    @Id
    @Column(name = "email")
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "password")
    private String password;

    @Column(name = "verified")
    private int verified;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "favourites",
        joinColumns = {@JoinColumn(name = "email")},
        inverseJoinColumns = {@JoinColumn (name = "vessel_id")})
    private Set<Vessel> favouritedVessels;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "subscriptions",
            joinColumns = {@JoinColumn(name = "email")},
            inverseJoinColumns = {@JoinColumn (name = "vessel_id")})
    private Set<Vessel> subscribedVessels;

    public Account() {
    }

    public Account(String email, String password) {
        this.email = email;
        this.password = password;
        this.verified = 0;
        this.favouritedVessels = null;
        this.subscribedVessels = null;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getVerified() {
        return verified;
    }

    public void setVerified(int verified) {
        this.verified = verified;
    }

    public Set<Vessel> getFavouritedVessels() {
        return favouritedVessels;
    }

    public void setFavouritedVessels(Set<Vessel> favouritedVessels) {
        this.favouritedVessels = favouritedVessels;
    }

    // to associate favourited vessel to this account
    public void addFavouritedVessel(Vessel vessel) {
        this.favouritedVessels.add(vessel);
        Set<Account> retrievedAccounts = vessel.getFavouritedByAccounts();
        retrievedAccounts.add(this);
        vessel.setFavouritedByAccounts(retrievedAccounts);
    }

    //  to disassociate favourited vessel from this account
    public void removeFavouritedVessel(Vessel vessel) {
        this.favouritedVessels.remove(vessel);
        Set<Account> retrievedAccounts = vessel.getFavouritedByAccounts();
        retrievedAccounts.remove(this);
        vessel.setFavouritedByAccounts(retrievedAccounts);
    }

    // disassociate all favourited vessels from this account, used when deleting the account
    public void removeAllFavouritedVessels() {
        for (Vessel vessel : new ArrayList<>(favouritedVessels)) {
            removeFavouritedVessel(vessel);
        }
    }

    public Set<Vessel> getSubscribedVessels() {
        return subscribedVessels;
    }

    public void setSubscribedVessels(Set<Vessel> subscribedVessels) {
        this.subscribedVessels = subscribedVessels;
    }

    // to associate new subscribed vessel to this account
    public void addSubscribedVessel(Vessel vessel) {
        this.subscribedVessels.add(vessel);
        Set<Account> retrieveAccounts = vessel.getSubscribedByAccounts();
        retrieveAccounts.add(this);
        vessel.setSubscribedByAccounts(retrieveAccounts);
    }

    //  to disassociate subscribed vessel from this account
    public void removeSubscribedVessel(Vessel vessel) {
        this.subscribedVessels.remove(vessel);
        Set<Account> retrievedAccounts = vessel.getSubscribedByAccounts();
        retrievedAccounts.remove(this);
        vessel.setSubscribedByAccounts(retrievedAccounts);
    }

    // disassociate all subscribed vessels from this account, used when deleting the account
    public void removeAllSubscribedVessels() {
        for (Vessel vessel : new ArrayList<>(subscribedVessels)) {
            removeSubscribedVessel(vessel);
        }
    }
}
