package com.IS442.teamsixtester.services;

import com.IS442.teamsixtester.dao.Vessel.VesselDAO;
import com.IS442.teamsixtester.model.Vessel.Vessel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class VesselService {
    private final VesselDAO vesselDao;

    @Autowired
    public VesselService(@Qualifier("postgres") VesselDAO vesselDao) {
        this.vesselDao = vesselDao;
    }

    public int addVessel(Vessel vessel) {
        return vesselDao.insertVessel(vessel);
    }

    public List<Vessel> getAllVessels() {
        return vesselDao.selectAllVessels();
    }

    public Optional<Vessel> getIncomingVessel(String vesselName, String incomingVoyageNum) {
        return vesselDao.selectVesselByIncoming(vesselName, incomingVoyageNum);
    }

    public Optional<Vessel> getOutgoingVessel(String vesselName, String outgoingVoyageNum) {
        return vesselDao.selectVesselByOutgoing(vesselName, outgoingVoyageNum);
    }

    public void deleteVessel(UUID id) {
        vesselDao.deleteVesselById(id);
    }

    public int updateVessel(UUID id, Vessel vessel) {
        return vesselDao.updateVessel(id, vessel);
    }
}