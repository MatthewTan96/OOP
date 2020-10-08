package com.IS442.teamsixtester.services;

//import com.IS442.teamsixtester.dao.Vessel.VesselDAO;
import com.IS442.teamsixtester.dao.Vessel.VesselRepository;
import com.IS442.teamsixtester.model.Vessel.Vessel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

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
}