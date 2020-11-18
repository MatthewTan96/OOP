package com.IS442.teamsixtester.controllers;

import com.IS442.teamsixtester.api.AccountAPI;
import com.IS442.teamsixtester.model.Account.AccountDTO;
import com.IS442.teamsixtester.services.AccountService;
import com.google.common.hash.Hashing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.*;
import com.IS442.teamsixtester.model.Account.Account;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.validation.Valid;
import java.nio.charset.StandardCharsets;
import java.util.List;

@CrossOrigin(origins="*", allowedHeaders = "*")

@RestController
public class AccountController implements AccountAPI {
    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @Autowired
    private JavaMailSender mailSender;

    @Override
    @PostMapping(value = ACCOUNT_PATH_CREATE)
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

    @Override
    @GetMapping(value = ACCOUNT_PATH_GET_ONE)
    public ResponseEntity getAccountByEmail(@RequestParam String email) {
        return ResponseEntity.ok(accountService.getAccountByEmail(email));
    }

    @Override
    @DeleteMapping(value = ACCOUNT_PATH_DELETE)
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

    @Override
    @GetMapping(value = ACCOUNT_PATH_GET_ALL)
    public ResponseEntity<List<Account>> accountGetAll() {
        return ResponseEntity.ok(accountService.accountGetAll());
    }

    @Override
    @PostMapping(value = ACCOUNT_PATH_AUTHENTICATE)
    public ResponseEntity accountCheckAuthenticate(@Valid @RequestBody AccountDTO accountDTO
    ) {
        String email = accountDTO.getEmail();
        String password = accountDTO.getPassword();
        if (accountService.getAccountByEmail(email) == null){
            return ResponseEntity.ok("2"); //no email exist
        }
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

    @Override
    @PutMapping(value = ACCOUNT_PATH_UPDATE_PW)
    public ResponseEntity updatePassword(@Valid @RequestBody AccountDTO accountToChange) {
        String email = accountToChange.getEmail();
        String newPassword = accountToChange.getPassword();
        Account accountToChangePw = accountService.getAccountByEmail(email);
        String hashedNewPassword = Hashing.sha256().hashString(newPassword, StandardCharsets.UTF_8).toString();
        accountService.changePassword(accountToChangePw,hashedNewPassword);
        return ResponseEntity.ok("Password Successfully Changed");
    }

    @Override
    @PutMapping(value = ACCOUNT_PATH_SET_VERIFICATION)
    public ResponseEntity setVerification(@RequestParam String email) {
        Account accountToChangeVerification = accountService.getAccountByEmail(email);
        accountService.changeVerified(accountToChangeVerification);
        return ResponseEntity.ok("Verification Successfully Changed");
    }

    @Override
    @GetMapping(value = ACCOUNT_PATH_SEND_VERIFICATION)
    public ResponseEntity<String> verificationSend(@RequestParam String email, @RequestParam String code) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        //get the email of the user
        String mailContent =  "<body>\n" +
                "\n" +
                "<h3> Dear user, here is the verification code to verify your account. </h3> \n  "
                + "<h4> Verification code: " + code + "</h4></body>";

        helper.setTo(email);
        helper.setText(mailContent,true);
        helper.setSubject("Verification code for PSA");
        mailSender.send(message);

        return ResponseEntity.ok("Verification Code Sent Sucessfully");
    }
    
}