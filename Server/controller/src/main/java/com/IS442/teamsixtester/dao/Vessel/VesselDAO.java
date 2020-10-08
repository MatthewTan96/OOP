//package com.IS442.teamsixtester.dao.Vessel;
//
//import com.IS442.teamsixtester.model.Vessel.Vessel;
//
//import java.util.List;
//import java.util.Optional;
//import java.util.UUID;
//
//public interface VesselDAO {
//    Vessel insertVessel(Vessel vessel);
//
////    default int insertVessel(Vessel vessel) {
////        UUID id = UUID.randomUUID();
////        return insertVessel(id, vessel);
////    }
//
//    List<Vessel> selectAllVessels();
//
//    Optional<Vessel> selectVesselByIncoming(String name, String incoming);
//
//    Optional<Vessel> selectVesselByOutgoing(String name, String outgoing);
//
//    int deleteVesselById(UUID id);
//
//    int updateVessel(UUID id, Vessel vessel);
//
//    }
