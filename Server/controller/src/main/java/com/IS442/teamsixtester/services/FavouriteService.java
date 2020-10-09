package com.IS442.teamsixtester.services;

import com.IS442.teamsixtester.model.Account.Account;
import com.IS442.teamsixtester.dao.Vessel.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.IS442.teamsixtester.dao.Vessel.VesselRepository;
import com.IS442.teamsixtester.model.Vessel.Vessel;
import com.IS442.teamsixtester.model.Vessel.VesselDTO;

import java.util.HashSet;
import java.util.Set;


@Service
public class FavouriteService {
    @Autowired
    private VesselRepository vesselrepository;

    @Autowired
    private AccountRepository accountrepository;



//    public void addFavourite(Vessel vessel, Account account) {
//        Set<Vessel> currentVessels = account.getVessels();
//        currentVessels.add(vessel);
//
//    }

    public void addFavourite(Set<Vessel> oldSetVessel, Set<Account> oldSetAccount,Vessel vessel, Account account) {
        Set<Vessel> newSetVessel = new HashSet<Vessel>();
        for (Vessel oldVessel : oldSetVessel) {
            newSetVessel.add(oldVessel);
        }
        newSetVessel.add(vessel);

        account.setVessels(newSetVessel);

        Set<Account> newSetAccount = new HashSet<Account>();
        for (Account oldAccount : oldSetAccount) {
            newSetAccount.add(oldAccount);
        }
        newSetAccount.add(account);
        vessel.setAccounts(newSetAccount);

        vesselrepository.save(vessel);
        accountrepository.save(account);
    }

}