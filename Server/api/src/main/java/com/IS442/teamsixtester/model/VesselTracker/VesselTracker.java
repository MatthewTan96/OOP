package com.IS442.teamsixtester.model.VesselTracker;

//
//import java.io.Serializable;
import java.util.ArrayList;
import java.util.Set;

import com.IS442.teamsixtester.model.Account.Account;
import com.IS442.teamsixtester.model.Vessel.Vessel;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;


public class VesselTracker{
    private Multimap userAndSubscribedVessels;

    public VesselTracker(){this.userAndSubscribedVessels = ArrayListMultimap.create(); }

    public Multimap<Account, ArrayList<Vessel>> getUserAndSubscribedVessels() {
        return userAndSubscribedVessels;
    }

    public void setUserAndSubscribedVessels(Multimap<Account, ArrayList<Vessel>> userAndSubscribedVessels) {
        this.userAndSubscribedVessels = userAndSubscribedVessels;
    }

    public void addVessel(Account user, Vessel vessel){ this.userAndSubscribedVessels.put(user,vessel); }

//    @Override
//    public String toString() {
//        return "Vessel{" +
//                "vessel=" + vesselId +
//                '}';
//    }
}