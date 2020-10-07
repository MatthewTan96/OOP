package com.IS442.teamsixtester.api;

import com.IS442.teamsixtester.model.Vessel.Vessel;
import com.IS442.teamsixtester.model.Vessel.VesselDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.ResponseEntity;

public interface VesselAPI {
    ResponseEntity vesselPost(
            VesselDTO vesselDTO
    ) throws JsonProcessingException;

    ResponseEntity vesselGetAll();

    ResponseEntity vesselGet(
            String abbrVslM,
            String inVoyN,
            String outVoyN
    );

    ResponseEntity vesselDelete(
            String abbrVslM,
            String inVoyN,
            String outVoyN
    );

    ResponseEntity vesselUpdate(
            Vessel vessel
    ) throws JsonProcessingException;

    String VESSEL_VERSION = "/v1";
    String VESSEL_BASE_PATH = VESSEL_VERSION + "/vessel";
}
