package com.IS442.teamsixtester.controller;

import com.IS442.teamsixtester.domain.Vessel;
import com.IS442.teamsixtester.service.VesselService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class VesselController {
    @Autowired
    VesselService service;

    @RequestMapping(value="/vessel/{id}", method= RequestMethod.GET)
    public ResponseEntity<Vessel> getVesselById(@PathVariable Integer id) {
        return new ResponseEntity<Vessel>(service.findById(id), HttpStatus.OK);
    }

    @RequestMapping(value="/vessel/", method=RequestMethod.GET)
    public ResponseEntity<List<Vessel>> getVessels() {
        return new ResponseEntity<List<Vessel>>(service.getAll(), HttpStatus.OK);
    }

    @RequestMapping(value="/create/", method=RequestMethod.POST)
    public ResponseEntity<String> createVessel(@RequestBody Vessel vessel) {
        service.create(vessel);
        return new ResponseEntity<String>("Vessel Created Successfully", HttpStatus.CREATED);
    }

    @RequestMapping(value="/update/{id}", method=RequestMethod.PUT)
    public ResponseEntity<String> updateVessel(@PathVariable Integer id, @RequestBody Vessel updatedVessel) {
        if (service.findById(id) == null) {
            return new ResponseEntity<String>("Account not found", HttpStatus.NOT_FOUND);
        }
        service.update(updatedVessel);
        return new ResponseEntity<String>("Account updated successfully", HttpStatus.OK);
    }

    @RequestMapping(value="/delete/{id}", method=RequestMethod.DELETE)
    public ResponseEntity<String> deleteVessel(@PathVariable Integer id) {
        if (service.findById(id) == null) {
            return new ResponseEntity<String>("Account not found", HttpStatus.NOT_FOUND);
        }
        service.delete(id);
        return new ResponseEntity<String>("Vessel deleted", HttpStatus.OK);
    }
}