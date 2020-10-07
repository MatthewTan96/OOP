package com.IS442.teamsixtester.controllers;


import com.IS442.teamsixtester.api.VesselAPI;
import com.IS442.teamsixtester.model.Vessel.Vessel;
import com.IS442.teamsixtester.model.Vessel.VesselDTO;
import com.IS442.teamsixtester.services.VesselService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
public class VesselController implements VesselAPI {

    private final VesselService vesselService;

    @Autowired
    public VesselController(VesselService vesselService) {
        this.vesselService = vesselService;
    }

    @RequestMapping(value="/postVessel/", method=RequestMethod.POST)
    public ResponseEntity<String> vesselPost(@RequestBody Vessel vessel) {
        String incoming = vessel.getInVoyN();
        Optional<Vessel> queryVessel = Optional.empty();
        if (incoming == null || incoming.isBlank()){
            queryVessel = vesselService.getOutgoingVessel(vessel.getAbbrVslM(), vessel.getOutVoyN());
        } else {
            queryVessel = vesselService.getIncomingVessel(vessel.getAbbrVslM(), vessel.getInVoyN());
        }
        if (queryVessel.isPresent()) {
            return new ResponseEntity<String>("Vessel exists", HttpStatus.BAD_REQUEST);
        }
        vesselService.addVessel(vessel);
        return new ResponseEntity<String>("created", HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity vesselPost(VesselDTO vesselDTO) throws JsonProcessingException {
        return null;
    }

    @RequestMapping(value="/getAllVessels/", method=RequestMethod.GET)
    public ResponseEntity<List<Vessel>> vesselGetAll() {
        return new ResponseEntity<List<Vessel>>(vesselService.getAllVessels(), HttpStatus.OK) ;
    }

    @Override
    public ResponseEntity vesselGet(String abbrVslM, String inVoyN, String outVoyN) {
        return null;
    }

    @Override
    public ResponseEntity vesselDelete(String abbrVslM, String inVoyN, String outVoyN) {
        return null;
    }

    @Override
    public ResponseEntity vesselUpdate(Vessel vessel) throws JsonProcessingException {
        return null;
    }

    @RequestMapping(value="/getVessel/", method=RequestMethod.GET)
    public ResponseEntity<?> vesselGet(@RequestBody(required=false) Map<String, String> json) {
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
//    public ResponseEntity<?> vesselUpdate(@RequestMapping(required=false) Map<String, String> json) {
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
    public ResponseEntity<?> vesselDelete(@RequestBody(required = false) Map<String, String> json) {
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