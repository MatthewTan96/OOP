package com.IS442.teamsixtester.model.Account;

import com.IS442.teamsixtester.model.Vessel.Vessel;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Entity
@Table(name = "login_info")
public class Account {
    @Id
    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "verified")
    private int verified;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "favourites",
        joinColumns = {@JoinColumn(name = "email")},
        inverseJoinColumns = {@JoinColumn (name = "vessel_id")})
    private Set<Vessel> vessels;

    public Account() {
    }

    public Account(String email, String password) {
        this.email = email;
        this.password = password;
        this.verified = 0;
        this.vessels = null;
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

    public Set<Vessel> getVessels() {
        return vessels;
    }

    public void setVessels(Set<Vessel> vessels) {
        this.vessels = vessels;
    }
}
