package com.IS442.teamsixtester.controllers;

import com.IS442.teamsixtester.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.IS442.teamsixtester.model.Account.Account;

import java.util.List;

@CrossOrigin(origins="*", allowedHeaders = "*")
@RestController
public class AccountController {
    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

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

    @GetMapping(value="/getAccountByEmail")
    public Account getAccountByEmail(@RequestParam
                                     String email){
        return accountService.getAccountByEmail(email);
    }

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

    @GetMapping(value = "/getAllAccounts")
    public ResponseEntity<List<Account>> accountGetAll() {
        return ResponseEntity.ok(accountService.accountGetAll());
    }
}
