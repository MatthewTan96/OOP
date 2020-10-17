package com.IS442.teamsixtester.dao.Account;

import com.IS442.teamsixtester.model.Account.Account;
import com.IS442.teamsixtester.repositories.Account.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository("postgres1")
public class PostgresAccountDataAccessService implements AccountDAO{


    private final AccountRepository accountRepository;

    @Autowired
    public PostgresAccountDataAccessService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Account insertAccount(Account account) {
        return accountRepository.save(account);
    }

    @Override
    public List<Account> selectAllAccount() {
        List<Account> accounts = new ArrayList<>();
        accountRepository.findAll().forEach((accounts::add));
        return accounts;
    }

    @Override
    public Account getAccountByEmail(String email) {
        return accountRepository.findTopByEmail(email);
    }

    @Override
    public void deleteAccount(Account accountToDelete) {
        accountRepository.delete(accountToDelete);
    }

    @Override
    public Account updateAccount(Account existingAccount, Account toChangeAccount) {
        existingAccount.setPassword(toChangeAccount.getPassword());
        existingAccount.setVerified(toChangeAccount.getVerified());
        return accountRepository.save(existingAccount);
    }
}
