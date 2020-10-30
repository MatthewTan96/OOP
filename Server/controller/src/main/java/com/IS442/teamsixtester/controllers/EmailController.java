package com.IS442.teamsixtester.controllers;


import com.IS442.teamsixtester.model.Account.Account;
import com.IS442.teamsixtester.model.Vessel.Vessel;
import com.IS442.teamsixtester.model.VesselTracker.VesselTracker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.mail.internet.MimeMessage;
import javax.validation.Valid;
import java.lang.reflect.Array;
import java.util.List;
import java.util.Set;

@Controller
public class EmailController {

    @Autowired
    private JavaMailSender mailSender;

    @PostMapping("/testsendEmail")
    public ResponseEntity sendEmail(
            @RequestParam String email,
            @RequestParam String name
    ){
//          Send text
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("teamsixoop@gmail.com");
        message.setTo(email);

        String mailSubject = "HELLO TEST OOP SUBJECT SIX" + name;
        String mailContent = "HELLO \n can work alot \n \n \n \n test test";

        message.setSubject(mailSubject);
        message.setText(mailContent);
//        //Send html message
//
//        MimeMessage message = mailSender.createMimeMessage();
//        MimeMessageHelper helper = new MimeMessageHelper(message);

        mailSender.send(message);
        return ResponseEntity.ok(message);
    }

    @PostMapping("/sendEmail")
    public ResponseEntity sendEmail(
            @Valid @RequestBody VesselTracker accountAndVessels){

        //get from VesselTracker Object
        for (Account key: accountAndVessels.getUserAndSubscribedVessels().keySet()){
            System.out.println(key);
            System.out.println(accountAndVessels.getUserAndSubscribedVessels().get(key));
        }


//        System.out.println();
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setFrom("teamsixoop@gmail.com");
//        message.setTo("email");
//
//        String mailSubject = "";
//        String mailContent = "";
//
//        message.setSubject(mailSubject);
//        message.setText(mailContent);
//
//        mailSender.send(message);
        return ResponseEntity.ok("hello");
    }


}
