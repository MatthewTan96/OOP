package com.IS442.teamsixtester.controllers;

import com.IS442.teamsixtester.model.Account.AccountDTO;
import com.IS442.teamsixtester.services.AccountService;
import com.google.common.hash.Hashing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.IS442.teamsixtester.model.Account.Account;

import java.nio.charset.StandardCharsets;
import java.util.List;

@CrossOrigin(origins="*", allowedHeaders = "*")
@RestController
public class AccountController {
    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping(value = "/postAccount")
    public ResponseEntity accountPost(@RequestBody AccountDTO accountDTO) {
        String email = accountDTO.getEmail();
        String password = accountDTO.getPassword();
        Account checkIfExist1 = accountService.getAccountByEmail(email);
        //hash password
        String hashedPassword = Hashing.sha256().hashString(password, StandardCharsets.UTF_8).toString();
        if (checkIfExist1 != null) {
            return ResponseEntity.badRequest().build();
        }
        accountDTO.setPassword(hashedPassword);
        Account newAccount = accountService.addAccount(accountDTO.toTrueClass());
        return ResponseEntity.ok(newAccount);
    }

    @GetMapping(value = "/getAccountByEmail")
    public Account getAccountByEmail(@RequestParam String email) {
        return accountService.getAccountByEmail(email);
    }

    @DeleteMapping(value = "/deleteAccount")
    public ResponseEntity accountDelete(@RequestBody AccountDTO accountDTO) {
        String email = accountDTO.getEmail();
        String password = accountDTO.getPassword();
        String hashedPassword = Hashing.sha256().hashString(password, StandardCharsets.UTF_8).toString();
        Account accountToDelete = accountService.getAccountByEmail(email);
        if (accountToDelete == null) {
            return ResponseEntity.notFound().build();
        }
        if (!(hashedPassword.equals(accountToDelete.getPassword()))) {
            return ResponseEntity.badRequest().build();
        }
        accountService.deleteAccount(accountToDelete);
        return ResponseEntity.ok(accountToDelete);
    }

    @GetMapping(value = "/getAllAccounts")
    public ResponseEntity<List<Account>> accountGetAll() {
        return ResponseEntity.ok(accountService.accountGetAll());
    }

    @PostMapping(value = "/authenticate")
    public ResponseEntity accountCheckAuthenticate(
            @RequestParam String email,
            @RequestParam String password
    ) {
        Account accountToCheck = accountService.getAccountByEmail(email);
        String hashedPassword = Hashing.sha256().hashString(password, StandardCharsets.UTF_8).toString();
        if (accountToCheck.getPassword().equals(hashedPassword)){
            if(accountToCheck.getVerified() == 1){ //correct credential and verified
                return ResponseEntity.ok("1");
            } else if(accountToCheck.getVerified() == 0){
                return ResponseEntity.ok("0"); //correct credential but not verified
            }
        } else {
            return ResponseEntity.ok("2");//wrong credential
        }
        return ResponseEntity.ok(accountService.accountGetAll());
    }

    @PutMapping(value = "/updatePassword")
    public ResponseEntity updatePassword(@RequestParam String email,
                                         @RequestParam String newPassword) {
        Account accountToChangePw = accountService.getAccountByEmail(email);
        String oldpw = accountToChangePw.getPassword();
        String hashedNewPassword = Hashing.sha256().hashString(newPassword, StandardCharsets.UTF_8).toString();
        accountService.changePassword(accountToChangePw,hashedNewPassword);
        return ResponseEntity.ok("Password Successfully Changed");
    }

    @PutMapping(value = "/setVerification")
    public ResponseEntity setVerification(@RequestParam String email) {
        Account accountToChangeVerification = accountService.getAccountByEmail(email);
        accountService.changeVerified(accountToChangeVerification);
        return ResponseEntity.ok("Verification Successfully Changed");
    }




    //update password
    //check password
    //hash password
    //check authenicated - if verified 1, if not verified 0, wrong credential 2
}