package com.IS442.teamsixtester.services;

//import com.IS442.teamsixtester.dao.Vessel.VesselDAO;
import com.IS442.teamsixtester.model.Account.Account;
import com.IS442.teamsixtester.dao.Vessel.AccountRepository;
import com.IS442.teamsixtester.model.Vessel.Vessel;
import com.IS442.teamsixtester.model.Vessel.VesselDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    public Account getAccountByEmail(String email) {
        return accountRepository.findTopByEmail(email);
    }

    public Account updateAccount(Account existingAccount, Account toChangeAccount) {
        existingAccount.setPassword(toChangeAccount.getPassword());
        existingAccount.setVerified(toChangeAccount.getVerified());
        return accountRepository.save(existingAccount);
    }

    public List<Account> accountGetAll() {
        List<Account> accounts = new ArrayList<>();
        accountRepository.findAll().forEach((accounts::add));
        return accounts;
    }
}