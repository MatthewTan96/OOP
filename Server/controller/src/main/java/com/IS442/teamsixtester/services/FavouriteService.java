package com.IS442.teamsixtester.services;

import com.IS442.teamsixtester.model.Account.Account;
import com.IS442.teamsixtester.repositories.Account.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.IS442.teamsixtester.model.Vessel.Vessel;


@Service
public class FavouriteService {
    @Autowired
    private AccountRepository accountRepository;


    public void addFavourite(Vessel vessel, Account account) {
        account.addFavouritedVessel(vessel);
        accountRepository.save(account);
    }

    public void deleteFavourite(Vessel vessel, Account account) {
        account.removeFavouritedVessel(vessel);
        accountRepository.save(account);
    }
}