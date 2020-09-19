package com.IS442.teamsixtester.service;

import com.IS442.teamsixtester.domain.Vessel;
import com.IS442.teamsixtester.repository.VesselRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class VesselService {
    @Autowired
    VesselRepository repository;
//    Map<Long, Vessel> database = new HashMap<>();

    public Vessel findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public List<Vessel> getAll() {
        return repository.findAll();
    }
    public void create(Vessel vessel) {
        repository.save(vessel);
    }
    public void update(Vessel updatedVessel) {
        Vessel vesselToBeUpdated = repository.findById(updatedVessel.getId()).orElse(null);
        if (vesselToBeUpdated == null) {
            return;
        }
        // logic for degree of change
        LocalDateTime originalBerthTime = LocalDateTime.parse(vesselToBeUpdated.getFirstBerthTime());
        LocalDateTime newBerthTime = LocalDateTime.parse(updatedVessel.getBerthTimeRequired());
        long dateTimeDiff = ChronoUnit.HOURS.between(originalBerthTime, newBerthTime);

        // update Vessel object with new values
        vesselToBeUpdated.setBerthNumber(updatedVessel.getBerthNumber());
        vesselToBeUpdated.setBerthTimeRequired(updatedVessel.getBerthTimeRequired());
        vesselToBeUpdated.setExpectedDateTimeDeparture(updatedVessel.getExpectedDateTimeDeparture());
        vesselToBeUpdated.setIncomingVoyageNumber(updatedVessel.getIncomingVoyageNumber());
        vesselToBeUpdated.setOutgoingVoyageNumber(updatedVessel.getOutgoingVoyageNumber());
        vesselToBeUpdated.setStatus(updatedVessel.getStatus());
        vesselToBeUpdated.setVesselShortName(updatedVessel.getVesselShortName());
        vesselToBeUpdated.setChangeCount(vesselToBeUpdated.getChangeCount() + 1);
        vesselToBeUpdated.setDegreeChange(dateTimeDiff);
        repository.save(vesselToBeUpdated);
    }

    public void delete (Long id) {
        Vessel vesselToBeDeleted = repository.findById(id).orElse(null);
        if (vesselToBeDeleted == null) {
            return;
        }
        repository.delete(vesselToBeDeleted);
    }
}