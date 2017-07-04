package org.dominwos.finder;

import org.dominwos.exceptions.PathfindingException;
import org.dominwos.model.Route;
import org.dominwos.model.Station;

import java.util.*;
import java.util.stream.Collectors;

public class RouteFinder {

    private final List<Route> routes;
    private final Map<Station, Integer> distances;
    private final Set<Station> stationsToCheck;
    private final Set<Station> stationsChecked;
    private final Map<Station, Station> predecessors;

    public RouteFinder(List<Route> routes) {
        this.routes = routes;
        this.distances = new HashMap<>();
        this.stationsChecked = new HashSet<>();
        this.stationsToCheck = new HashSet<>();
        this.predecessors = new HashMap<>();
    }

    public void calculateRoutesFromSation(Station begginingStation) {
        Map<Station, Integer> distances = new HashMap<>();
        distances.put(begginingStation, 0);

        stationsToCheck.add(begginingStation);
        while (stationsToCheck.size() != 0) {
            Optional<Station> nextStationOptional = getClosestStation(stationsToCheck);
            Station nextStation = nextStationOptional.orElseThrow(() ->
                    new PathfindingException("Error while fiding path"));
            stationsChecked.add(nextStation);
            stationsToCheck.remove(nextStation);
            findMinimalPossibleTravelTimes(nextStation);
        }
    }

    private Optional<Station> getClosestStation(Set<Station> stations) {
        Optional<Station> closestStation = Optional.empty();
        for (Station station : stations) {
            if (!closestStation.isPresent()) {
                closestStation = Optional.of(station);
            } else {
                if (getShortestTravelTime(station) < getShortestTravelTime(closestStation.get())) {
                    closestStation = Optional.of(station);
                }
            }
        }
        return closestStation;
    }

    private void findMinimalPossibleTravelTimes(Station beginningStation) {
        List<Station> neighbouringStations = getNeighbouringStations(beginningStation);
        neighbouringStations.forEach(station -> {
            if (getShortestTravelTime(station) > getShortestTravelTime(beginningStation) + getTravelTime(beginningStation, station)) {
                distances.put(
                        station, getShortestTravelTime(beginningStation) + getTravelTime(beginningStation, station)
                );
                predecessors.put(station, beginningStation);
                stationsToCheck.add(station);
            }
        });
    }

    private int getTravelTime(Station beggining, Station destination) {
        Route destinationRoute = routes.stream()
                .filter(route -> route.getBeginningStation().equals(beggining)
                        && route.getDestinationStation().equals(destination))
                .findFirst()
                .orElseThrow(() -> new PathfindingException("Not possible to find a route between given stations."));

        return destinationRoute.getTravelTime();
    }

    private List<Station> getNeighbouringStations(final Station station) {
        return routes.stream()
                .filter(potentialNeighbour ->
                        potentialNeighbour.getBeginningStation().equals(station)
                                && !stationsChecked.contains(potentialNeighbour.getDestinationStation()))
                .map(route -> route.getDestinationStation())
                .collect(Collectors.toList());
    }

        private int getShortestTravelTime(Station destinationStation) {
        if (!distances.containsKey(destinationStation)) {
            return Integer.MAX_VALUE;
        } else {
            return distances.get(destinationStation);
        }
    }

    public List<Station> getQuickestTravelRoute(Station destination) {
        if(!predecessors.containsKey(destination)) {
            return new ArrayList<>();
        }
        List<Station> stations = new LinkedList<>();
        stations.add(destination);
        Station nextStation = destination;
        while(predecessors.containsKey(nextStation)){
            nextStation = predecessors.get(nextStation);
            stations.add(nextStation);
        }
        Collections.reverse(stations);
        return stations;
    }

    public int getTravelTime(List<Station> quickestRoute) {
        int totalTravelTime = 0;
        for(int i =0 ; i < quickestRoute.size() - 1 ; i++) {
            totalTravelTime += getTravelTime(quickestRoute.get(i),quickestRoute.get(i+1));
        }
        return totalTravelTime;
    }
}