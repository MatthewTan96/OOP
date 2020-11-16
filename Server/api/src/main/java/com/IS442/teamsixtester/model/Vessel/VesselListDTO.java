package com.IS442.teamsixtester.model.Vessel;

import java.util.ArrayList;
import java.util.List;

public class VesselListDTO {

    private List<VesselDTO> vessels;

    public VesselListDTO() {
        this.vessels = new ArrayList<>();
    }

    public VesselListDTO(List<VesselDTO> listVesselDTO) {
        this.vessels = listVesselDTO;
    }

    public List<VesselDTO> getListVesselDTO() {
        return vessels;
    }

    public void setListVesselDTO(List<VesselDTO> listVesselDTO) {
        this.vessels = listVesselDTO;
    }
}
