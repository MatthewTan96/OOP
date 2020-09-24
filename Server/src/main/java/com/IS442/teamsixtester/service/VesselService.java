package com.IS442.teamsixtester.service;

import com.IS442.teamsixtester.dao.VesselDao;
import com.IS442.teamsixtester.model.Vessel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class VesselService {
    private final VesselDao vesselDao;

    @Autowired
    public VesselService(@Qualifier("postgres") VesselDao vesselDao) {
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

    public Optional<Vessel> getVesselByName(String vesselName, String incomingVoyageNum, String outgoingVoyageNum) {
        if ((outgoingVoyageNum == null || outgoingVoyageNum.isBlank()) &&
                (incomingVoyageNum != null && !(incomingVoyageNum.isBlank()))) {
            return  vesselDao.selectVesselByIncoming(vesselName, incomingVoyageNum);
        }
        else if ((incomingVoyageNum == null || incomingVoyageNum.isBlank()) &&
                (outgoingVoyageNum != null && !(outgoingVoyageNum.isBlank()))) {
            return vesselDao.selectVesselByOutgoing(vesselName, outgoingVoyageNum);
        }
        return vesselDao.selectVesselByIncoming(vesselName, incomingVoyageNum);
    }

    public int deleteVessel(UUID id) {
        return vesselDao.deleteVesselById(id);
    }

    public int updateVessel(UUID id, Vessel vessel) {
        return vesselDao.updateVessel(id, vessel);
    }
    //    Map<Long, Vessel> database = new HashMap<>();

//    public List<Vessel> findByName(String vesselName, String incomingVoyage, String outgoingVoyage) {
//        return repository.findByNameAndInOut(vesselName, incomingVoyage, ).orElse(null);
//    }
//
//    public List<Vessel> getAll() {
//        return repository.findAll();
//    }
//    public void create(Vessel vessel) {
//        repository.save(vessel);
//    }
//    public void update(Vessel updatedVessel) {
//        Vessel vesselToBeUpdated = repository.findById(updatedVessel.getVessel_id()).orElse(null);
//        if (vesselToBeUpdated == null) {
//            return;
//        }
//        // logic for degree of change
//        LocalDateTime originalBerthTime = LocalDateTime.parse(vesselToBeUpdated.getFirst_berth_time());
//        LocalDateTime newBerthTime = LocalDateTime.parse(updatedVessel.getBerth_time_required());
//        long dateTimeDiff = ChronoUnit.HOURS.between(originalBerthTime, newBerthTime);
//
//        // update Vessel object with new values
//        vesselToBeUpdated.setBerth_number(updatedVessel.getBerth_number());
//        vesselToBeUpdated.setBerth_number(updatedVessel.getBerth_number());
//        vesselToBeUpdated.setExpected_datetime_departure(updatedVessel.getExpected_datetime_departure());
//        vesselToBeUpdated.setIncoming_voyage_number(updatedVessel.getIncoming_voyage_number());
//        vesselToBeUpdated.setOutgoing_voyage_number(updatedVessel.getOutgoing_voyage_number());
//        vesselToBeUpdated.setStatus(updatedVessel.getStatus());
//        vesselToBeUpdated.setVessel_short_name(updatedVessel.getVessel_short_name());
//        vesselToBeUpdated.setChange_count(vesselToBeUpdated.getChange_count() + 1);
//        vesselToBeUpdated.setDegree_change(dateTimeDiff);
//        repository.save(vesselToBeUpdated);
//    }
//
//    public void delete (Integer id) {
//        Vessel vesselToBeDeleted = repository.findById(id).orElse(null);
//        if (vesselToBeDeleted == null) {
//            return;
//        }
//        repository.delete(vesselToBeDeleted);
//    }
}