package com.IS442.teamsixtester.dao.Domain;

import com.IS442.teamsixtester.model.Domain.Domain;
import com.IS442.teamsixtester.repositories.Domain.DomainRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Optional;

@CrossOrigin(origins = "*", allowedHeaders = "*") // CrossOrigin allows front end to use data from Java
@Repository("postgres2")
public class PostgresDomainDataAccessService implements DomainDAO{
    private final DomainRepository domainRepository;

    @Autowired
    public PostgresDomainDataAccessService(DomainRepository domainRepository) {
        this.domainRepository = domainRepository;
    }

    @Override
    public Domain getDomain(Domain domain) {
        Optional<Domain> result = domainRepository.findById(domain.getDomain());
        if (result.isEmpty()) {
            return null;
        }
        return result.get();
    }

    @Override
    public Domain insertDomain(Domain domain) {
        return domainRepository.save(domain);
    }
}
