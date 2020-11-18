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
import javax.mail.MessagingException;
import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*") // CrossOrigin allows front end to use data from Java
@RestController
public class VesselController implements VesselAPI {

    @Autowired
    private VesselService vesselService;

    @PostMapping(value = "/bulkUpdate")
    public ResponseEntity bulkUpdateVessel(
            @Valid @RequestBody String json) {
        try{
            vesselService.bulkUpdate(json);
        }
        catch (MessagingException e){
            return ResponseEntity.badRequest().body("Messaging Exception Error");
        } catch (JsonProcessingException e) {
            return ResponseEntity.badRequest().body("JsonProcessingError");
        }

        return ResponseEntity.ok("Vessels Updated Successfully");
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