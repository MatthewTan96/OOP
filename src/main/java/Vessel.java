package main.java;

// import main.java;

public class Vessel {
    String vesselShortName;
    String incomingVoyageNumber;
    String outgoingVoyageNumber;
    String berthTimeRequired;
    String expectedDateTimeDeparture;
    String berthNumber;
    String status; 

    public Vessel(String vesselShortName,String incomingVoyageNumber,String outgoingVoyageNumber,
    String berthTimeRequired,String expectedDateTimeDeparture,String berthNumber,String status){
        
        this.vesselShortName = vesselShortName;
        this.incomingVoyageNumber = incomingVoyageNumber;
        this.outgoingVoyageNumber = outgoingVoyageNumber;
        this.berthTimeRequired = berthTimeRequired;
        this.expectedDateTimeDeparture = expectedDateTimeDeparture;
        this.berthNumber = berthNumber;
        this.status = status;
    }

    public String getVesselShortName(){
        return this.vesselShortName;
    }

    public String getIncomingVoyageNumber(){
        return this.incomingVoyageNumber;
    }

    public String outgoingVoyageNumber(){
        return this.outgoingVoyageNumber;
    }

    public String getBerthTimeRequired(){
        return this.berthTimeRequired;
    }

    public String getExpectedDateTimeDeparture(){
        return this.getExpectedDateTimeDeparture();
    }

    public String getBerthNumber(){
        return this.berthNumber;
    }

    public String status(){
        return this.status;
    }

    public String toString(){
        return "{"+
            "vesselShortName: " + this.vesselShortName +','
            + "incomingVoyageNumber" + this.incomingVoyageNumber+','
            + "outgoingVoyageNumber" + this.outgoingVoyageNumber+','
            + "berthTimeRequired" + this.berthTimeRequired+','
            + "expectedDateTimeDeparture" + this.expectedDateTimeDeparture+','
            + "berthNumber" + this.berthNumber+','
            + "status" + this.status+ "}";
    }
}
