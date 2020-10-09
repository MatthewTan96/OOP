package com.IS442.teamsixtester.model.Vessel;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

public class VesselQueryDTO implements Serializable {
    @NotBlank
    private String abbrVslM;

    private String inVoyN;

    private String outVoyN;

    public VesselQueryDTO() {
    }

    public VesselQueryDTO(@NotBlank String abbrVslM, String inVoyN, String outVoyN) {
        this.abbrVslM = abbrVslM;
        this.inVoyN = inVoyN;
        this.outVoyN = outVoyN;
    }

    public String getAbbrVslM() {
        return abbrVslM;
    }

    public void setAbbrVslM(String abbrVslM) {
        this.abbrVslM = abbrVslM;
    }

    public String getInVoyN() {
        return inVoyN;
    }

    public void setInVoyN(String inVoyN) {
        this.inVoyN = inVoyN;
    }

    public String getOutVoyN() {
        return outVoyN;
    }

    public void setOutVoyN(String outVoyN) {
        this.outVoyN = outVoyN;
    }
}
