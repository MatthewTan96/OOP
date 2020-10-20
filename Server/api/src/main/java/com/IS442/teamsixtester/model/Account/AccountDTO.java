package com.IS442.teamsixtester.model.Account;

import com.IS442.teamsixtester.model.DTO;

import java.io.Serializable;

public class AccountDTO implements Serializable, DTO {
    private String email;
    private String password;

    public AccountDTO() {
    }

    public AccountDTO(String email, String password) {
        this.email = email;
        this.password = password;
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

    @Override
    public String toString() {
        return "AccountDTO{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    @Override
    public Account toTrueClass() {
        return new Account(this.email, this.password);
    }
}
