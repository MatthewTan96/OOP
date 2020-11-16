package com.IS442.teamsixtester.dao.Domain;

import com.IS442.teamsixtester.model.Domain.Domain;

public interface DomainDAO {
    Domain getDomain(Domain domain);

    Domain insertDomain(Domain domain);
}
