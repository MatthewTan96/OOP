package com.IS442.teamsixtester.controllers;

import com.IS442.teamsixtester.services.AccountService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.IS442.teamsixtester.model.Account.Account;

import javax.validation.Valid;

@RestController
public class AccountController {

    @Autowired
    private AccountService accountService = new AccountService();

//    @Override
    @PostMapping(value="/postAccount")
    public ResponseEntity accountPost(@RequestBody Account account){
//        String email = account.getEmail();
//        Account checkIfExist1 = accountService.getAccountByEmail(email);
//        if (checkIfExist1 != null) {
//            return ResponseEntity.badRequest().build();
//        }
        Account newAccount = accountService.addAccount(account);
        return ResponseEntity.ok(newAccount);
    }

//    @Override
    @PostMapping(value="/deleteAccount")
    public ResponseEntity accountDelete(Account account) {
        String email = account.getEmail();
        Account accountToDelete = accountService.getAccountByEmail(email);
        if (accountToDelete == null) {
            return ResponseEntity.notFound().build();
        }
        accountService.deleteAccount(accountToDelete);
        return ResponseEntity.ok(accountToDelete);
    }

//    @Override
    @PostMapping(value="/updateAccount")
    public ResponseEntity accountUpdate(Account account) throws JsonProcessingException {
        return null;
    }
}
