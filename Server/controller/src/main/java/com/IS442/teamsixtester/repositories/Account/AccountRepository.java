package com.IS442.teamsixtester.repositories.Account;
import com.IS442.teamsixtester.model.Account.Account;

import org.springframework.data.repository.CrudRepository;

public interface AccountRepository extends CrudRepository<Account,String> {
    Account findTopByEmail(String email);
}
