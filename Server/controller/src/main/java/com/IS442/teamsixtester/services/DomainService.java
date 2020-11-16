package com.IS442.teamsixtester.services;

import com.IS442.teamsixtester.dao.Domain.DomainDAO;
import com.IS442.teamsixtester.model.Domain.Domain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "*", allowedHeaders = "*") // CrossOrigin allows front end to use data from Java
@Service
public class DomainService {
    private final DomainDAO domainDAO;

    @Autowired
    public DomainService(@Qualifier("postgres2") DomainDAO domainDAO) {
        this.domainDAO = domainDAO;
    }

    public Domain addDomain(Domain domain) {
        return domainDAO.insertDomain(domain);
    }

    public Domain getDomain(Domain domain) {
        return domainDAO.getDomain(domain);
    }
}
