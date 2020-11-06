package com.IS442.teamsixtester.model.VesselTracker;

import com.IS442.teamsixtester.model.Vessel.Vessel;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class VesselTracker{
    private Multimap userAndSubscribedVessels;

    public VesselTracker(){this.userAndSubscribedVessels = ArrayListMultimap.create(); }

    @Bean("VesselTracker")
    public VesselTracker newTracker() {
        return new VesselTracker();
    }

    public Multimap<String, Vessel> getUserAndSubscribedVessels() {
        return userAndSubscribedVessels;
    }

    public void setUserAndSubscribedVessels(Multimap<String, Vessel> userAndSubscribedVessels) {
        this.userAndSubscribedVessels = userAndSubscribedVessels;
    }

    public void addVessel(String email, Vessel vessel) {
            userAndSubscribedVessels.put(email,vessel);


    }

}