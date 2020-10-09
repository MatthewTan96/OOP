package com.IS442.teamsixtester.dao.Vessel;
import com.IS442.teamsixtester.model.Account.Account;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface AccountRepository extends CrudRepository<Account,String> {
    Account findAccountByEmail(String email);
}
