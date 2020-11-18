package com.IS442.teamsixtester.api;

import com.IS442.teamsixtester.model.Vessel.VesselQueryDTO;
import org.springframework.http.ResponseEntity;

public interface VesselAPI {

    ResponseEntity vesselGetAll();

    ResponseEntity vesselGet(VesselQueryDTO vesselQueryDTO
    );

    ResponseEntity vesselDelete(VesselQueryDTO vesselQueryDTO
    );

    ResponseEntity bulkUpdateVessel(String json);

    String VESSEL_PATH_BULK_UPDATE = "/bulkUpdate";

    String VESSEL_PATH_GET_ALL = "/getAllVessels";

    String VESSEL_PATH_GET_ONE = "/getVessel";

    String VESSEL_PATH_DELETE = "/deleteVessel";
}
