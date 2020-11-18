package com.IS442.teamsixtester.controllers;

import com.IS442.teamsixtester.api.DomainAPI;
import com.IS442.teamsixtester.model.Domain.Domain;
import com.IS442.teamsixtester.services.DomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins="*", allowedHeaders = "*")
@RestController
public class DomainController implements DomainAPI {
    private final DomainService domainService;

    @Autowired
    public DomainController(DomainService domainService) {
        this.domainService = domainService;
    }

    @CrossOrigin
    @Override
    @PostMapping(value=DOMAIN_PATH_POST)
    public ResponseEntity domainPost(@RequestBody Domain domain) {
        return ResponseEntity.ok(domainService.addDomain(domain));
    }

    @CrossOrigin
    @Override
    @GetMapping(value=DOMAIN_PATH_GET)
    public ResponseEntity getDomain(@RequestParam String email) {
        Domain domain = new Domain(email);
        return ResponseEntity.ok(domainService.getDomain(domain));
    }

}
