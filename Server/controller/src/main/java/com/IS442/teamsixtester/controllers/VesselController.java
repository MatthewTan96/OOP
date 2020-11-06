package com.IS442.teamsixtester.controllers;


import com.IS442.teamsixtester.api.VesselAPI;
import com.IS442.teamsixtester.model.Account.Account;
import com.IS442.teamsixtester.model.Vessel.Vessel;
import com.IS442.teamsixtester.model.Vessel.VesselDTO;
import com.IS442.teamsixtester.model.Vessel.VesselQueryDTO;
import com.IS442.teamsixtester.model.VesselTracker.VesselTracker;
import com.IS442.teamsixtester.services.VesselService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.collect.Multimap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@CrossOrigin(origins = "*", allowedHeaders = "*") // CrossOrigin allows front end to use data from Java
@RestController
public class VesselController implements VesselAPI {

    @Autowired
    private VesselService vesselService;

    @Resource
    private VesselTracker VesselTracker;

    @Autowired
    private JavaMailSender mailSender;


    @Override
    @PostMapping(value = "/postVessel")
    public ResponseEntity<Vessel> vesselPost(
            @Valid @RequestBody VesselDTO vesselDTO) {
        Vessel checkIfExist1 = vesselService.getVesselByOutgoing(vesselDTO.getAbbrVslM(), vesselDTO.getOutVoyN());

//        Vessel checkIfExist2 = vesselService.getVesselByIncoming(vesselDTO.getAbbrVslM(), vesselDTO.getInVoyN());

        if (checkIfExist1 != null) {
            Vessel newVessel = vesselService.updateVessel(checkIfExist1, vesselDTO);
            Set<Account> AccountsSubscribed = newVessel.getSubscribedByAccounts();
            for (Account account : AccountsSubscribed) {
                VesselTracker.addVessel(account.getEmail(), newVessel);
            }
            return ResponseEntity.ok(newVessel);
        }

        Vessel newVessel = vesselService.addVessel(vesselDTO.toTrueClass());
        return ResponseEntity.ok(newVessel);
    }
    //test
    @GetMapping(value = "/hello")
    public ResponseEntity<?> getTracker(){
        return ResponseEntity.ok(VesselTracker.getUserAndSubscribedVessels().asMap());
    }

    @Override
    @GetMapping(value = "/getAllVessels")
    public ResponseEntity<List<Vessel>> vesselGetAll() {
        return ResponseEntity.ok(vesselService.getAllVessels());
    }

    @Override
    @GetMapping(value = "/getVessel")
    public ResponseEntity vesselGet(@Valid @RequestBody VesselQueryDTO vesselQueryDTO) {
        String name = vesselQueryDTO.getAbbrVslM();
        String incoming = vesselQueryDTO.getInVoyN();
        String outgoing = vesselQueryDTO.getOutVoyN();
        if (incoming == null || incoming.isBlank()) {
            return ResponseEntity.ok(vesselService.getVesselByOutgoing(name, outgoing));
        } else if (outgoing == null || outgoing.isBlank()) {
            return ResponseEntity.ok(vesselService.getVesselByIncoming(name, incoming));
        } else {
            return ResponseEntity.ok(vesselService.getVesselByIncoming(name, incoming));
        }
    }

    @Override
    @DeleteMapping(value = "/deleteVessel")
    public ResponseEntity vesselDelete(@Valid @RequestBody VesselQueryDTO vesselQueryDTO) {
        String name = vesselQueryDTO.getAbbrVslM();
        String incoming = vesselQueryDTO.getInVoyN();
        String outgoing = vesselQueryDTO.getOutVoyN();
        Vessel vesselToDelete = null;
        if (incoming == null || incoming.isBlank()) {
            vesselToDelete = vesselService.getVesselByOutgoing(name, outgoing);
        } else if (outgoing == null || outgoing.isBlank()) {
            vesselToDelete = vesselService.getVesselByIncoming(name, incoming);
        } else {
            vesselToDelete = vesselService.getVesselByOutgoing(name, outgoing);
        }
        if (vesselToDelete == null) {
            return ResponseEntity.notFound().build();
        }
        Vessel deletedVessel = vesselService.deleteVessel(vesselToDelete);
        return ResponseEntity.ok(deletedVessel);
    }

    @Override
    public ResponseEntity vesselUpdate(Vessel vessel) throws JsonProcessingException {
        return null;
    }

    @PostMapping("/sendEmail") //send email
    public ResponseEntity<?> sendEmail() throws MessagingException {

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        //get the email of the user
        for (String email: VesselTracker.getUserAndSubscribedVessels().keySet()){
            helper.setTo(email);
            String mailContent = "<html>\n" +
                    "<head>\n" +
                    "<style>\n" +
                    "table {\n" +
                    "  font-family: arial, sans-serif;\n" +
                    "  border-collapse: collapse;\n" +
                    "  width: 100%;\n" +
                    "}\n" +
                    "\n" +
                    "td, th {\n" +
                    "  border: 1px solid #dddddd;\n" +
                    "  text-align: left;\n" +
                    "  padding: 8px;\n" +
                    "}\n" +
                    "\n" +
                    "tr:nth-child(even) {\n" +
                    "  background-color: #77c3ec;\n" +
                    "}\n" +
                    "</style>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "\n" +
                    "<h2>Vessel Berthing Time Change Notification</h2>\n" +
                    "\n" +
                    "<h3> Dear user, please note that there is the changes to the berthing time for the following vessels you have subscribed to.</h3> \n" +
                    "<table>\n" +
                    "  <tr>\n" +
                    "    <th>Vessel Short Name</th>\n" +
                    "    <th>Previous Berthing Time</th>\n" +
                    "    <th>New Berthing Time</th>\n" +
                    "  </tr>" ;

            // get all vessels subscribed
            for (Vessel s : VesselTracker.getUserAndSubscribedVessels().get(email)) {
                mailContent += "<tr>\n" +
                        "    <td>" + s.getAbbrVslM()  +"</td>\n" +
                        "    <td>" + s.getFirstBerthTime() +
                        "    <td>" + s.getBthgDt() +
                        "  </tr>";
            }

            mailContent += "</table>\n" +
                    "\n" +
                    "</body>\n" +
                    "</html>\n";

            helper.setText(mailContent,true);
            helper.setSubject("PSA - Vessel Changes Notification");
            mailSender.send(message);

        }

        VesselTracker.getUserAndSubscribedVessels().clear();

        return ResponseEntity.ok(VesselTracker.getUserAndSubscribedVessels().asMap());
    }
}