package com.IS442.teamsixtester.controller;

import com.IS442.teamsixtester.model.Vessel;
import com.IS442.teamsixtester.service.VesselService;
import org.apache.coyote.Request;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
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
        return new ResponseEntity<Optional<Vessel>>(vesselService.getVesselByName(json.get("vesselShortName"),
                json.get("incomingVoyageNumber"), json.get("outgoingVoyageNumber")), HttpStatus.OK);
    }


    @RequestMapping(value="deleteVessel/{id}", method=RequestMethod.DELETE)
    public void deleteVesselById(@PathVariable("id") UUID id) {
        vesselService.deleteVessel(id);
    }

    @RequestMapping(value="updateVessel/{id}", method=RequestMethod.PUT)
    public void updateVesselById(@PathVariable("id") UUID id, @Valid @NonNull @RequestBody Vessel vessel) {
        vesselService.updateVessel(id, vessel);
    }
    //    @RequestMapping(value="/getVesselSchedule/", method= RequestMethod.GET)
//    public ResponseEntity<?> getVesselById(@RequestBody Map<String, String> json) {
//        if (json.get("vessel_short_name") == null || json.get("vessel_short_name").isBlank()) {
//            return new ResponseEntity<Error>(HttpStatus.BAD_REQUEST);
//        }
//        if ((json.get("incoming_voyage_number") == null || json.get("incoming_voyage_number").isBlank()) &&
//                (json.get("outgoing_voyage_number") == null || json.get("outgoing_voyage_number").isBlank())) {
//            return new ResponseEntity<Error>(HttpStatus.BAD_REQUEST);
//        }
//        return new ResponseEntity<List<Vessel>>(service.findByName(json.get("vessel_short_name"),
//                                                                    json.get("incoming_voyage_number"),
//                                                                    json.get("outgoing_voyage_number")),
//                                                                    HttpStatus.OK);
//    }
//
//    @RequestMapping(value="/vessel/", method=RequestMethod.GET)
//    public ResponseEntity<List<Vessel>> getVessels() {
//        return new ResponseEntity<List<Vessel>>(service.getAll(), HttpStatus.OK);
//    }
//
//    @RequestMapping(value="/create/", method=RequestMethod.POST)
//    public ResponseEntity<String> createVessel(@RequestBody Vessel vessel) {
//        service.create(vessel);
//        return new ResponseEntity<String>("Vessel Created Successfully", HttpStatus.CREATED);
//    }
//
//    @RequestMapping(value="/update/", method=RequestMethod.PUT)
//    public ResponseEntity<String> updateVessel(@RequestBody Vessel updatedVessel) {
//        if (service.findById(id) == null) {
//            return new ResponseEntity<String>("Account not found", HttpStatus.NOT_FOUND);
//        }
//        service.update(updatedVessel);
//        return new ResponseEntity<String>("Account updated successfully", HttpStatus.OK);
//    }
//
//    @RequestMapping(value="/delete/{id}", method=RequestMethod.DELETE)
//    public ResponseEntity<String> deleteVessel(@PathVariable Integer id) {
//        if (service.findById(id) == null) {
//            return new ResponseEntity<String>("Account not found", HttpStatus.NOT_FOUND);
//        }
//        service.delete(id);
//        return new ResponseEntity<String>("Vessel deleted", HttpStatus.OK);
//    }
}