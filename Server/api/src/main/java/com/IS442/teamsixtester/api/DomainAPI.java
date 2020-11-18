package com.IS442.teamsixtester.api;

import com.IS442.teamsixtester.model.Domain.Domain;
import org.springframework.http.ResponseEntity;

public interface DomainAPI {
    ResponseEntity domainPost(Domain domain);

    ResponseEntity getDomain(String email);

    String DOMAIN_PATH_POST = "/postDomain";

    String DOMAIN_PATH_GET = "/getDomain";
}
