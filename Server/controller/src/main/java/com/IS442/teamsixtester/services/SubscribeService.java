package com.IS442.teamsixtester.services;

import com.IS442.teamsixtester.model.Account.Account;
import com.IS442.teamsixtester.model.Vessel.Vessel;
import com.IS442.teamsixtester.repositories.Account.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "*", allowedHeaders = "*") // CrossOrigin allows front end to use data from Java
@Service
public class SubscribeService {
    @Autowired
    private AccountRepository accountRepository;


    public void addSubscribe(Vessel vessel, Account account) {
        account.addSubscribedVessel(vessel);
        accountRepository.save(account);
    }

    public void deleteSubscribe(Vessel vessel, Account account) {
        account.removeSubscribedVessel(vessel);
        accountRepository.save(account);
    }
}
