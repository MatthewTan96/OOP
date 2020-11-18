package com.IS442.teamsixtester.api;

import com.IS442.teamsixtester.model.Account.AccountDTO;
import org.springframework.http.ResponseEntity;

import javax.mail.MessagingException;

public interface AccountAPI {
    ResponseEntity accountPost(AccountDTO accountDTO);

    ResponseEntity getAccountByEmail(String email);

    ResponseEntity accountDelete(AccountDTO accountDTO);

    ResponseEntity accountGetAll();

    ResponseEntity accountCheckAuthenticate(AccountDTO accountDTO);

    ResponseEntity updatePassword(AccountDTO accountDTO);

    ResponseEntity setVerification(String email);

    ResponseEntity verificationSend(String email, String code) throws MessagingException;

    String ACCOUNT_PATH_CREATE = "/postAccount";

    String ACCOUNT_PATH_GET_ONE = "/getAccountByEmail";

    String ACCOUNT_PATH_DELETE = "/deleteAccount";

    String ACCOUNT_PATH_GET_ALL = "/getAllAccounts";

    String ACCOUNT_PATH_AUTHENTICATE = "/authenticate";

    String ACCOUNT_PATH_UPDATE_PW = "/updatePassword";

    String ACCOUNT_PATH_SET_VERIFICATION = "/setVerification";

    String ACCOUNT_PATH_SEND_VERIFICATION = "/sendVerification";


}
