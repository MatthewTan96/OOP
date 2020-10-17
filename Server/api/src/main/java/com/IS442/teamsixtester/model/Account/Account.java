package com.IS442.teamsixtester.model.Account;

import com.IS442.teamsixtester.model.Vessel.Vessel;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
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

    public Account() {
    }

    public Account(String email, String password) {
        this.email = email;
        this.password = password;
        this.verified = 0;
        this.favouritedVessels = null;
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

    // to associate new vessel to this account
    public void addVessel(Vessel vessel) {
        this.favouritedVessels.add(vessel);
        Set<Account> retrievedAccounts = vessel.getFavouritedByAccounts();
        retrievedAccounts.add(this);
        vessel.setFavouritedByAccounts(retrievedAccounts);
    }

    //  to disassociate vessel from this account
    public void removeVessel(Vessel vessel) {
        this.favouritedVessels.remove(vessel);
        Set<Account> retrievedAccounts = vessel.getFavouritedByAccounts();
        retrievedAccounts.remove(this);
        vessel.setFavouritedByAccounts(retrievedAccounts);
    }

    // disassociate all vessels from this account, used when deleting the account
    public void remove() {
        for (Vessel vessel : new ArrayList<>(favouritedVessels)) {
            removeVessel(vessel);
        }
    }
}
