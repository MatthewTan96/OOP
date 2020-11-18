package com.IS442.teamsixtester.controllers;


import com.IS442.teamsixtester.model.Account.Account;
import com.IS442.teamsixtester.model.Vessel.Vessel;
import com.IS442.teamsixtester.services.FavouriteService;
import com.IS442.teamsixtester.services.VesselService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.IS442.teamsixtester.services.AccountService;


@CrossOrigin(origins = "*", allowedHeaders = "*") // CrossOrigin allows front end to use data from Java
@RestController
public class FavouriteController {

    @Autowired
    private FavouriteService favouriteService;

    @Autowired
    private VesselService vesselService;

    @Autowired
    private AccountService accountService;

    @PostMapping(value = "/postFavourite")
    public ResponseEntity favouritePost(
            @RequestParam String vesselShortName,
            @RequestParam String incoming,
            @RequestParam String email
    ){

        Vessel vesselToFavourite = vesselService.getVesselByIncoming(vesselShortName, incoming);
        Account account = accountService.getAccountByEmail(email);

        if (vesselToFavourite == null || account == null) {
            return ResponseEntity.badRequest().build();
        }

        favouriteService.addFavourite(vesselToFavourite,account);
        return ResponseEntity.ok(account);
    }

    @DeleteMapping(value = "/deleteFavourite")
    public ResponseEntity favouriteDelete(
            @RequestParam String vesselShortName,
            @RequestParam String incoming,
            @RequestParam String email
    ) {
        Vessel vesselToUnfavourite = vesselService.getVesselByIncoming(vesselShortName, incoming);
        Account account = accountService.getAccountByEmail(email);

        if (vesselToUnfavourite == null || account == null) {
            return ResponseEntity.badRequest().build();
        }
        favouriteService.deleteFavourite(vesselToUnfavourite, account);
        return ResponseEntity.ok(account);
    }
}