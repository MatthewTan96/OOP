package com.IS442.teamsixtester.controllers;

import com.IS442.teamsixtester.model.Account.Account;
import com.IS442.teamsixtester.model.Vessel.Vessel;
import com.IS442.teamsixtester.services.AccountService;
import com.IS442.teamsixtester.services.SubscribeService;
import com.IS442.teamsixtester.services.VesselService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SubscribeController {
    @Autowired
    private SubscribeService subscribeService;

    @Autowired
    private VesselService vesselService;

    @Autowired
    private AccountService accountService;

    @PostMapping(value = "/postSubscribe")
    public ResponseEntity favouritePost(
            @RequestParam String vesselShortName,
            @RequestParam String incoming,
            @RequestParam String email
    ){

        Vessel vesselToSubscribe = vesselService.getVesselByIncoming(vesselShortName, incoming);
        Account account = accountService.getAccountByEmail(email);

        if (vesselToSubscribe == null || account == null) {
            return ResponseEntity.badRequest().build();
        }

        subscribeService.addSubscribe(vesselToSubscribe,account);
        return ResponseEntity.ok(account);
    }

    @DeleteMapping(value = "/deleteSubscribe")
    public ResponseEntity favouriteDelete(
            @RequestParam String vesselShortName,
            @RequestParam String incoming,
            @RequestParam String email
    ) {
        Vessel vesselToUnsubscribe = vesselService.getVesselByIncoming(vesselShortName, incoming);
        Account account = accountService.getAccountByEmail(email);

        if (vesselToUnsubscribe == null || account == null) {
            return ResponseEntity.badRequest().build();
        }

        subscribeService.deleteSubscribe(vesselToUnsubscribe, account);
        return ResponseEntity.ok(account);
    }
}
