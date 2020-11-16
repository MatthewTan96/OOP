package com.IS442.teamsixtester.controllers;

import com.IS442.teamsixtester.model.Domain.Domain;
import com.IS442.teamsixtester.services.DomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins="*", allowedHeaders = "*")
@RestController
public class DomainController {
    private final DomainService domainService;

    @Autowired
    public DomainController(DomainService domainService) {
        this.domainService = domainService;
    }

    @CrossOrigin
    @PostMapping(value="/postDomain")
    public ResponseEntity domainPost(@RequestBody Domain domain) {
        return ResponseEntity.ok(domainService.addDomain(domain));
    }

    @CrossOrigin
    @GetMapping(value="/getDomain")
    public ResponseEntity getDomain(@RequestBody Domain domain) {
        return ResponseEntity.ok(domainService.getDomain(domain));
    }

}
