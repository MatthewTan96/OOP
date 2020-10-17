package com.IS442.teamsixtester.services;

import com.IS442.teamsixtester.model.Account.Account;
import com.IS442.teamsixtester.repositories.Account.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.IS442.teamsixtester.repositories.Vessel.VesselRepository;
import com.IS442.teamsixtester.model.Vessel.Vessel;

import java.util.Set;


@Service
public class FavouriteService {
    @Autowired
    private VesselRepository vesselRepository;

    @Autowired
    private AccountRepository accountRepository;


    public void addFavourite(Vessel vessel, Account account) {
//        Set<Vessel> currentVessels = account.getVessels();
//        currentVessels.add(vessel);
//        account.setVessels(currentVessels);
        account.addVessel(vessel);
        accountRepository.save(account);
    }

    public void deleteFavourite(Vessel vessel, Account account) {
        account.removeVessel(vessel);
        accountRepository.save(account);
    }
}