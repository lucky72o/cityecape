package com.cityescape.domain.mongo;

// All travel times are in minutes
public class TravelTimeDistance {

    private String startingPoint;
    private long publicTransportTravelTime;
    private long carTravelTime;

    public long getPublicTransportTravelTime() {
        return publicTransportTravelTime;
    }

    public void setPublicTransportTravelTime(long publicTransportTravelTime) {
        this.publicTransportTravelTime = publicTransportTravelTime;
    }

    public String getStartingPoint() {
        return startingPoint;
    }

    public void setStartingPoint(String startingPoint) {
        this.startingPoint = startingPoint;
    }

    public long getCarTravelTime() {
        return carTravelTime;
    }

    public void setCarTravelTime(long carTravelTime) {
        this.carTravelTime = carTravelTime;
    }
}
