package com.IS442.teamsixtester.services;


import com.IS442.teamsixtester.dao.Account.AccountDAO;
import com.IS442.teamsixtester.model.Account.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*") // CrossOrigin allows front end to use data from Java
@Service
public class AccountService {
    private final AccountDAO accountDao;

    @Autowired
    public AccountService(@Qualifier("postgres1") AccountDAO accountDao) {
        this.accountDao = accountDao;
    }


    public Account addAccount(Account account) {
        return accountDao.insertAccount(account);
    }

    public void deleteAccount(Account account) {
        account.removeAllFavouritedVessels();
        account.removeAllSubscribedVessels();
        accountDao.deleteAccount(account);
    }

    public Account getAccountByEmail(String email) {
        return accountDao.getAccountByEmail(email);
    }

    public Account updateAccount(Account existingAccount, Account toChangeAccount) {
        return accountDao.updateAccount(existingAccount, toChangeAccount);
    }

    public List<Account> accountGetAll() {
        return accountDao.selectAllAccount();
    }

    public Account changePassword(Account existingAccount, String newPassword) {
        return accountDao.changePassword(existingAccount, newPassword);
    }

    public Account changeVerified(Account existingAccount) {
        return accountDao.changeVerified(existingAccount);
    }
}