package org.dominwos.model;

public class Route {
    private Station beginningStation;
    private Station destinationStation;

    public Route(Station firstStation, Station secondStation, int travelTime) {
        this.beginningStation = firstStation;
        this.destinationStation = secondStation;
        this.travelTime = travelTime;
    }

    public Station getBeginningStation() {
        return beginningStation;
    }

    public Station getDestinationStation() {
        return destinationStation;
    }

    public int getTravelTime() {
        return travelTime;
    }

    private int travelTime;
}
