package com.IS442.teamsixtester.dao.Vessel;

import com.IS442.teamsixtester.model.Vessel.Vessel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface VesselRepository extends CrudRepository<Vessel, UUID> {
    Vessel findTopByAbbrVslMAndInVoyN(String abbrVslM, String inVoyN);
    Vessel findTopByAbbrVslMAndOutVoyN(String abbrVslM, String outVoyN);
}
