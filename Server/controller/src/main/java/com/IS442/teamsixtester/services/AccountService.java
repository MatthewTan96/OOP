package com.IS442.teamsixtester.services;

//import com.IS442.teamsixtester.dao.Vessel.VesselDAO;
import com.IS442.teamsixtester.model.Account.Account;
import com.IS442.teamsixtester.dao.Vessel.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;

    public Account addAccount(Account account) {
        return accountRepository.save(account);
    }

    public void deleteAccount(Account account) {
        accountRepository.delete(account);
    }

    public Account getAccountByEmail(String email){
        return accountRepository.findTopByEmail(email);
    }
}