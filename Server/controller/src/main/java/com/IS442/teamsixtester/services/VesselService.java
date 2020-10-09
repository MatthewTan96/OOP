package com.IS442.teamsixtester.services;

//import com.IS442.teamsixtester.dao.Vessel.VesselDAO;
import com.IS442.teamsixtester.dao.Vessel.VesselRepository;
import com.IS442.teamsixtester.model.Vessel.Vessel;
import com.IS442.teamsixtester.model.Vessel.VesselDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class VesselService {
    @Autowired
    private VesselRepository repository;

    public Vessel addVessel(Vessel vessel) {
        return repository.save(vessel);
    }

    public List<Vessel> getAllVessels() {
        List<Vessel> vessels = new ArrayList<>();
        repository.findAll().forEach((vessels::add));
        return vessels;
    }

    public Vessel getVesselByIncoming(String abbrVslM, String inVoyN) {
        return repository.findTopByAbbrVslMAndInVoyN(abbrVslM, inVoyN);
    }

    public Vessel getVesselByOutgoing(String abbrVslM, String outVoyN) {
        return repository.findTopByAbbrVslMAndOutVoyN(abbrVslM, outVoyN);
    }

    public void deleteVessel(Vessel vessel) {
        repository.delete(vessel);
    }

    public Vessel updateVessel(Vessel existingVessel, VesselDTO toChangeVessel) {
        LocalDateTime firstBerthTime = LocalDateTime.parse(existingVessel.getFirstBerthTime());
        LocalDateTime changedBerthTime = LocalDateTime.parse(toChangeVessel.getBthgDt());
        LocalDateTime currentBerthTime = LocalDateTime.parse(existingVessel.getBthgDt());
        Duration duration = Duration.between(firstBerthTime, changedBerthTime);

        if (!(changedBerthTime.isEqual(currentBerthTime))) {
            existingVessel.setChangeCount(existingVessel.getChangeCount() + 1);
            double numberOfHours = (double) (duration.toHours());
            existingVessel.setDegreeChange(numberOfHours);
            existingVessel.setBthgDt(toChangeVessel.getBthgDt());

        }
        existingVessel.setAbbrVslM(toChangeVessel.getAbbrVslM());
        existingVessel.setInVoyN(toChangeVessel.getInVoyN());
        existingVessel.setOutVoyN(toChangeVessel.getOutVoyN());
        existingVessel.setUnbthgDt(toChangeVessel.getUnbthgDt());
        existingVessel.setBerthN(toChangeVessel.getBerthN());
        existingVessel.setStatus(toChangeVessel.getStatus());

        return repository.save(existingVessel);
    }
}