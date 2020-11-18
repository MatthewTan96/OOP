package com.IS442.teamsixtester.dao.Vessel;

import com.IS442.teamsixtester.model.Vessel.Vessel;

import java.util.List;

public interface VesselDAO {
    Vessel insertVessel(Vessel vessel);

    List<Vessel> selectAllVessels();

    Vessel selectVesselByIncoming(String abbrVslM, String inVoyN);

    Vessel selectVesselByOutgoing(String abbrVslM, String outVoyN);

    Vessel selectVesselByShortname(String abbrVslM);

    Vessel deleteVessel(Vessel vessel);

    Vessel updateVessel(Vessel vesselToUpdate);

    }