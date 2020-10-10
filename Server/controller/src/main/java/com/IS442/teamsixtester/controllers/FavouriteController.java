package com.IS442.teamsixtester.controllers;

import com.IS442.teamsixtester.api.VesselAPI;
import com.IS442.teamsixtester.model.Account.Account;
import com.IS442.teamsixtester.model.Vessel.Vessel;
import com.IS442.teamsixtester.model.Vessel.VesselDTO;
import com.IS442.teamsixtester.model.Vessel.VesselQueryDTO;
import com.IS442.teamsixtester.services.FavouriteService;
import com.IS442.teamsixtester.services.VesselService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import com.IS442.teamsixtester.services.AccountService;
import java.io.*;
import java.util.*;

@RestController
public class FavouriteController {

    @Autowired
    private FavouriteService favouriteService;

    @Autowired
    private VesselService vesselService;

    @Autowired
    private AccountService accountService;

    @PostMapping(value = "/postFavourites")
    public ResponseEntity<String> favouritePost(
            @RequestParam String vesselShortName,
            @RequestParam String incoming,
            @RequestParam String email
    ){

        Vessel vesselToAdd = vesselService.getVesselByIncoming(vesselShortName, incoming);
        Account account = accountService.getAccountByEmail(email);

        favouriteService.addFavourite(vesselToAdd,account);
        return new ResponseEntity<>("okay", HttpStatus.OK);
    }



}