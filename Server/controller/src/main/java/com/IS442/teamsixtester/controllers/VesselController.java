package com.IS442.teamsixtester.controllers;


import com.IS442.teamsixtester.api.VesselAPI;
import com.IS442.teamsixtester.model.Account.Account;
import com.IS442.teamsixtester.model.Vessel.Vessel;
import com.IS442.teamsixtester.model.Vessel.VesselDTO;
import com.IS442.teamsixtester.model.Vessel.VesselListDTO;
import com.IS442.teamsixtester.model.Vessel.VesselQueryDTO;
import com.IS442.teamsixtester.services.VesselService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.tomcat.util.json.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

@CrossOrigin(origins = "*", allowedHeaders = "*") // CrossOrigin allows front end to use data from Java
@RestController
public class VesselController implements VesselAPI {

    @Autowired
    private VesselService vesselService;

//    @Resource
//    private VesselTracker VesselTracker;

    @Autowired
    private JavaMailSender mailSender;


    @Override
    @PostMapping(value = "/postVessel")
    public ResponseEntity<Vessel> vesselPost(
            @Valid @RequestBody VesselDTO vesselDTO) {
        Vessel checkIfExist1 = vesselService.getVesselByOutgoing(vesselDTO.getAbbrVslM(), vesselDTO.getOutVoyN());

//        Vessel checkIfExist2 = vesselService.getVesselByIncoming(vesselDTO.getAbbrVslM(), vesselDTO.getInVoyN());

        //take in list of vesselDTO instead

        if (checkIfExist1 != null) {
            Vessel newVessel = vesselService.updateVessel(checkIfExist1, vesselDTO);
            Set<Account> AccountsSubscribed = newVessel.getSubscribedByAccounts();

//            for (Account account : AccountsSubscribed) {
//                VesselTracker.addVessel(account.getEmail(), newVessel);
//            }
            return ResponseEntity.ok(newVessel);
        }

        Vessel newVessel = vesselService.addVessel(vesselDTO.toTrueClass());

        return ResponseEntity.ok(newVessel);
    }


    @PostMapping(value = "/bulkUpdate")
    public ResponseEntity bulkUpdateVessel(
            @Valid @RequestBody String json) {
        try{
            vesselService.bulkUpdate(json);
        }
        catch (MessagingException e){
            return ResponseEntity.badRequest().body("Messaging Exception Error");
        }
        catch (ParseException js){
            return ResponseEntity.badRequest().body("JSON exception");
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok("OK");
    }

    //test
    @GetMapping(value = "/hello")
    public ResponseEntity<?> getTracker(){
        return ResponseEntity.ok("VesselTracker.getUserAndSubscribedVessels().asMap()");
    }

    //another test
//    @RequestMapping(value = "/test/admin", method = RequestMethod.GET)
//    public ResponseEntity<String> getAdmin(@RequestHeader String Authorization) {
//        return ResponseEntity.ok("Hello Admin");
//    }

    @GetMapping("/test/admin")
    public ResponseEntity<String> getAdmin(
            @RequestHeader String Authorization
    ) {
        return ResponseEntity.ok("Hello Admin");
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



}