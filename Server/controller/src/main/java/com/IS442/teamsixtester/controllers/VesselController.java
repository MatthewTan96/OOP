package com.IS442.teamsixtester.controllers;


import com.IS442.teamsixtester.api.VesselAPI;
import com.IS442.teamsixtester.model.Vessel.Vessel;
import com.IS442.teamsixtester.model.Vessel.VesselDTO;
import com.IS442.teamsixtester.model.Vessel.VesselQueryDTO;
import com.IS442.teamsixtester.services.VesselService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@CrossOrigin(origins = "*", allowedHeaders = "*") // CrossOrigin allows front end to use data from Java
@RestController
public class VesselController implements VesselAPI {

    @Autowired
    private VesselService vesselService;


    @Override
    @PostMapping(value = "/postVessel")
    public ResponseEntity<Vessel> vesselPost(
            @Valid @RequestBody VesselDTO vesselDTO) {
        Vessel checkIfExist1 = vesselService.getVesselByOutgoing(vesselDTO.getAbbrVslM(), vesselDTO.getOutVoyN());

        Vessel checkIfExist2 = vesselService.getVesselByIncoming(vesselDTO.getAbbrVslM(), vesselDTO.getInVoyN());

        if (checkIfExist1 != null) {
            Vessel newVessel = vesselService.updateVessel(checkIfExist1, vesselDTO);
            return ResponseEntity.ok(newVessel);
        }
        Vessel newVessel = vesselService.addVessel(vesselDTO.toTrueClass());
        return ResponseEntity.ok(newVessel);
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
        vesselService.deleteVessel(vesselToDelete);
        return ResponseEntity.ok(vesselToDelete);
    }

    @Override
    public ResponseEntity vesselUpdate(Vessel vessel) throws JsonProcessingException {
        return null;
    }
}