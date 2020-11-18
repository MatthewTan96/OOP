package com.IS442.teamsixtester.api;

import com.IS442.teamsixtester.model.Vessel.Vessel;
import com.IS442.teamsixtester.model.Vessel.VesselDTO;
import com.IS442.teamsixtester.model.Vessel.VesselQueryDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.ResponseEntity;

public interface VesselAPI {

    ResponseEntity vesselGetAll();

    ResponseEntity vesselGet(
            VesselQueryDTO vesselQueryDTO
    );

    ResponseEntity vesselDelete(
            VesselQueryDTO vesselQueryDTO
    );

    ResponseEntity vesselUpdate(
            Vessel vessel
    ) throws JsonProcessingException;


}
