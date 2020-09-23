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

    public Vessel findById(Integer id) {
        return repository.findById(id).orElse(null);
    }

    public List<Vessel> getAll() {
        return repository.findAll();
    }
    public void create(Vessel vessel) {
        repository.save(vessel);
    }
    public void update(Vessel updatedVessel) {
        Vessel vesselToBeUpdated = repository.findById(updatedVessel.getVessel_id()).orElse(null);
        if (vesselToBeUpdated == null) {
            return;
        }
        // logic for degree of change
        LocalDateTime originalBerthTime = LocalDateTime.parse(vesselToBeUpdated.getFirst_berth_time());
        LocalDateTime newBerthTime = LocalDateTime.parse(updatedVessel.getBerth_time_required());
        long dateTimeDiff = ChronoUnit.HOURS.between(originalBerthTime, newBerthTime);

        // update Vessel object with new values
        vesselToBeUpdated.setBerth_number(updatedVessel.getBerth_number());
        vesselToBeUpdated.setBerth_number(updatedVessel.getBerth_number());
        vesselToBeUpdated.setExpected_datetime_departure(updatedVessel.getExpected_datetime_departure());
        vesselToBeUpdated.setIncoming_voyage_number(updatedVessel.getIncoming_voyage_number());
        vesselToBeUpdated.setOutgoing_voyage_number(updatedVessel.getOutgoing_voyage_number());
        vesselToBeUpdated.setStatus(updatedVessel.getStatus());
        vesselToBeUpdated.setVessel_short_name(updatedVessel.getVessel_short_name());
        vesselToBeUpdated.setChange_count(vesselToBeUpdated.getChange_count() + 1);
        vesselToBeUpdated.setDegree_change(dateTimeDiff);
        repository.save(vesselToBeUpdated);
    }

    public void delete (Integer id) {
        Vessel vesselToBeDeleted = repository.findById(id).orElse(null);
        if (vesselToBeDeleted == null) {
            return;
        }
        repository.delete(vesselToBeDeleted);
    }
}