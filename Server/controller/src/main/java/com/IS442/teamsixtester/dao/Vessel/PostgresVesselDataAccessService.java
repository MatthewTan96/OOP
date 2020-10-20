package com.IS442.teamsixtester.dao.Vessel;

import com.IS442.teamsixtester.model.Vessel.Vessel;
import com.IS442.teamsixtester.repositories.Vessel.VesselRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@CrossOrigin(origins = "*", allowedHeaders = "*") // CrossOrigin allows front end to use data from Java
@Repository("postgres")
public class PostgresVesselDataAccessService implements VesselDAO{
    @Autowired
    private VesselRepository repository;

    @Override
    public Vessel insertVessel(Vessel vessel) {
        repository.save(vessel);
        return vessel;
    }

    @Override
    public List<Vessel> selectAllVessels() {
        List<Vessel> vessels = new ArrayList<>();
        repository.findAll().forEach((vessels::add));
        return vessels;
    }

    @Override
    public Vessel selectVesselByIncoming(String abbrVslM, String inVoyN) {
        return repository.findTopByAbbrVslMAndInVoyN(abbrVslM, inVoyN);
    }

    @Override
    public Vessel selectVesselByOutgoing(String abbrVslM, String outVoyN) {
        return repository.findTopByAbbrVslMAndOutVoyN(abbrVslM, outVoyN);
    }

    @Override
    public Vessel deleteVessel(Vessel vessel) {
        repository.delete(vessel);
        return vessel;
    }

    @Override
    public Vessel updateVessel(Vessel vesselToUpdate) {
        return repository.save(vesselToUpdate);
    }
}
