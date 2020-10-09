package com.IS442.teamsixtester.controllers;

import com.IS442.teamsixtester.dao.Vessel.AccountRepository;
import com.IS442.teamsixtester.model.Vessel.Vessel;
import com.IS442.teamsixtester.services.AccountService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.IS442.teamsixtester.model.Account.Account;

import javax.persistence.PreUpdate;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins="*", allowedHeaders = "*")
@RestController
public class AccountController {

    @Autowired
    private AccountService accountService = new AccountService();

//    @Override
    @PostMapping(value="/postAccount")
    public ResponseEntity accountPost(@RequestBody Account account){
        String email = account.getEmail();
        Account checkIfExist1 = accountService.getAccountByEmail(email);
        if (checkIfExist1 != null) {
            return ResponseEntity.badRequest().build();
        }
        Account newAccount = accountService.addAccount(account);
        return ResponseEntity.ok(newAccount);
    }

    @PostMapping(value="/getAccountByEmail")
    public Account getAccountByEmail(@RequestBody Account account){
        String email = account.getEmail();
        Account getAccount = accountService.getAccountByEmail(email);
        return getAccount;
    }

//    @Override
    @DeleteMapping(value="/deleteAccount")
    public ResponseEntity accountDelete(@RequestBody Account account) {
        String email = account.getEmail();
        Account accountToDelete = accountService.getAccountByEmail(email);
        if (accountToDelete == null) {
            return ResponseEntity.notFound().build();
        }
        accountService.deleteAccount(accountToDelete);
        return ResponseEntity.ok(accountToDelete);
    }

//    @Override
    @GetMapping(value = "/getAllAccounts")
    public ResponseEntity<List<Account>> accountGetAll() {
        return ResponseEntity.ok(accountService.accountGetAll());
    }

//    @Override

}
