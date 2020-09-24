package com.IS442.teamsixtester.controller;

import ch.qos.logback.core.pattern.parser.OptionTokenizer;
import com.IS442.teamsixtester.model.Vessel;
import com.IS442.teamsixtester.service.VesselService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
public class VesselController {

    private final VesselService vesselService;

    @Autowired
    public VesselController(VesselService vesselService) {
        this.vesselService = vesselService;
    }

    @RequestMapping(value="/postVessel/", method=RequestMethod.POST)
    public ResponseEntity<String> addVessel(@RequestBody Vessel vessel) {
        vesselService.addVessel(vessel);
        return new ResponseEntity<String>("created", HttpStatus.CREATED);
    }

    @RequestMapping(value="/getAllVessels/", method=RequestMethod.GET)
    public List<Vessel> getAllVessels() {
        return vesselService.getAllVessels();
    }

    @RequestMapping(value="/getVessel/", method=RequestMethod.GET)
    public ResponseEntity<?> getVessel(@RequestBody(required=false) Map<String, String> json) {
        String name = json.get("abbrVslM");
        String incoming = json.get("inVoyN");
        String outgoing = json.get("outVoyN");
        if (name == null || name.isBlank()) {
            return new ResponseEntity<String>("Please input the abbrVslM", HttpStatus.NOT_FOUND);
        }
        else if (incoming == null || incoming.isBlank()) {
            return new ResponseEntity<Optional<Vessel>>(vesselService.getOutgoingVessel(name, outgoing), HttpStatus.OK);
        }
        else if (outgoing == null || outgoing.isBlank()) {
            return new ResponseEntity<Optional<Vessel>>(vesselService.getIncomingVessel(name, incoming), HttpStatus.OK);
        }
        return new ResponseEntity<Optional<Vessel>>(vesselService.getOutgoingVessel(name, outgoing), HttpStatus.OK);
    }

//    @RequestMapping(value="/updateVessel/", method=RequestMethod.PUT)
//    public ResponseEntity<?> updateVesselById(@RequestMapping(required=false) Map<String, String> json) {
//        String name = json.get("abbrVslM");
//        String incoming = json.get("inVoyN");
//        String outgoing = json.get("outVoyN");
//        boolean vesselExist = false;
//        Optional<Vessel> vesselRetrieved = Optional.empty();
//        if (name == null || name.isBlank()) {
//            return new ResponseEntity<String>("Please input the vesselName", HttpStatus.NOT_FOUND);
//        }
//        else if (incoming == null || incoming.isBlank()) {
//            vesselRetrieved = vesselService.getOutgoingVessel(name, outgoing);
//            vesselExist = vesselRetrieved.isPresent();
//        } else if (outgoing == null || outgoing.isBlank()) {
//            vesselRetrieved = vesselService.getIncomingVessel(name, incoming);
//            vesselExist = vesselRetrieved.isPresent();
//        } else {
//            vesselRetrieved = vesselService.getIncomingVessel(name, incoming);
//            vesselExist = vesselRetrieved.isPresent();
//        }
//        if (!vesselExist) {
//            return new ResponseEntity<String>("Vessel does not exist", HttpStatus.NOT_FOUND);
//        }
//        Vessel vessel = vesselRetrieved.get();
//        UUID vesselId = vessel.getVesselId();
//        vesselService.deleteVessel(vesselId);
//        return new ResponseEntity<String>(json.toString(), HttpStatus.OK);
//    }

    @RequestMapping(value="/deleteVessel/", method=RequestMethod.DELETE)
    public ResponseEntity<?> deleteVesselById(@RequestBody(required = false) Map<String, String> json) {
        String name = json.get("abbrVslM");
        String incoming = json.get("inVoyN");
        String outgoing = json.get("outVoyN");
        boolean vesselExist = false;
        Optional<Vessel> vesselRetrieved = Optional.empty();
        if (name == null || name.isBlank()) {
            return new ResponseEntity<String>("Please input the vesselName", HttpStatus.NOT_FOUND);
        }
        else if (incoming == null || incoming.isBlank()) {
            vesselRetrieved = vesselService.getOutgoingVessel(name, outgoing);
            vesselExist = vesselRetrieved.isPresent();
        } else if (outgoing == null || outgoing.isBlank()) {
            vesselRetrieved = vesselService.getIncomingVessel(name, incoming);
            vesselExist = vesselRetrieved.isPresent();
        } else {
            vesselRetrieved = vesselService.getIncomingVessel(name, incoming);
            vesselExist = vesselRetrieved.isPresent();
        }
        if (!vesselExist) {
            return new ResponseEntity<String>("Vessel does not exist", HttpStatus.NOT_FOUND);
        }
        Vessel vessel = vesselRetrieved.get();
        UUID vesselId = vessel.getVesselId();
        vesselService.deleteVessel(vesselId);
        return new ResponseEntity<String>(json.toString(), HttpStatus.OK);
    }
}