package com.IS442.teamsixtester.model.Account;

import javax.validation.constraints.NotBlank;

public class Account {
    @NotBlank
    private String email;
    @NotBlank
    private String password;
    private int verified;

    public Account(String email, String password) {
        this.email = email;
        this.password = password;
        this.verified = 0;
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
}
