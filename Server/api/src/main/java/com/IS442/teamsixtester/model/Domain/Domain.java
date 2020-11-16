package com.IS442.teamsixtester.model.Domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="allowed_domains")
public class Domain {

    @Id
    @NotBlank
    @NotNull
    @Column(name="domain")
    private String domain;

    public Domain() {
    }

    public Domain(String domain) {
        this.domain = domain;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }
}
