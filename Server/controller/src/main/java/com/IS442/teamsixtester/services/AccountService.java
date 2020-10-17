package com.IS442.teamsixtester.services;

//import com.IS442.teamsixtester.repositories.Vessel.VesselDAO;
import com.IS442.teamsixtester.model.Account.Account;
import com.IS442.teamsixtester.repositories.Account.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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