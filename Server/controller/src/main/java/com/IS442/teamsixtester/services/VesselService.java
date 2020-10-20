package com.IS442.teamsixtester.services;

import com.IS442.teamsixtester.dao.Vessel.VesselDAO;
import com.IS442.teamsixtester.model.Vessel.Vessel;
import com.IS442.teamsixtester.model.Vessel.VesselDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*") // CrossOrigin allows front end to use data from Java
@Service
public class VesselService {

    public final VesselDAO vesselDao;

    @Autowired
    public VesselService(@Qualifier("postgres") VesselDAO vesselDao) {
        this.vesselDao = vesselDao;
    }



    public Vessel addVessel(Vessel vessel) {
        return vesselDao.insertVessel(vessel);
    }

    @CrossOrigin
    public List<Vessel> getAllVessels() {
        return vesselDao.selectAllVessels();
    }

    public Vessel getVesselByIncoming(String abbrVslM, String inVoyN) {
        return vesselDao.selectVesselByIncoming(abbrVslM, inVoyN);
    }

    public Vessel getVesselByOutgoing(String abbrVslM, String outVoyN) {
        return vesselDao.selectVesselByOutgoing(abbrVslM, outVoyN);
    }

    public Vessel deleteVessel(Vessel vessel) {
        vessel.removeAllFavouritedByAccounts();
        vessel.removeAllSubscribedByAccounts();
        return vesselDao.deleteVessel(vessel);
    }

    public Vessel updateVessel(Vessel existingVessel, VesselDTO toChangeVessel) {
        LocalDateTime firstBerthTime = LocalDateTime.parse(existingVessel.getFirstBerthTime());
        LocalDateTime changedBerthTime = LocalDateTime.parse(toChangeVessel.getBthgDt());
        LocalDateTime currentBerthTime = LocalDateTime.parse(existingVessel.getBthgDt());
        Duration duration = Duration.between(firstBerthTime, changedBerthTime);

        if (!(changedBerthTime.isEqual(currentBerthTime))) {
            existingVessel.setChangeCount(existingVessel.getChangeCount() + 1);
            double numberOfHours = Math.abs((double) (duration.toHours()));
            existingVessel.setDegreeChange(numberOfHours);
            existingVessel.setBthgDt(toChangeVessel.getBthgDt());

        }
        existingVessel.setAbbrVslM(toChangeVessel.getAbbrVslM());
        existingVessel.setInVoyN(toChangeVessel.getInVoyN());
        existingVessel.setOutVoyN(toChangeVessel.getOutVoyN());
        existingVessel.setUnbthgDt(toChangeVessel.getUnbthgDt());
        existingVessel.setBerthN(toChangeVessel.getBerthN());
        existingVessel.setStatus(toChangeVessel.getStatus());

        return vesselDao.updateVessel(existingVessel);
    }
}