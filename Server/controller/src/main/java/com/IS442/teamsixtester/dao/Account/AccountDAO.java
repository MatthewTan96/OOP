package com.IS442.teamsixtester.dao.Account;

import com.IS442.teamsixtester.model.Account.Account;


import java.util.List;

public interface AccountDAO {
    Account insertAccount(Account account);

    List<Account> selectAllAccount();

    Account getAccountByEmail(String email);

    void deleteAccount(Account accountToDelete);

    Account updateAccount(Account existingAccount, Account newAccount);

    Account changePassword(Account existingAccount, String newPassword);

    Account changeVerified(Account existingAccount);
}
