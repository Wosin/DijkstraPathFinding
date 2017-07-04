package org.dominwos;

import org.dominwos.finder.RouteFinder;
import org.dominwos.model.Route;
import org.dominwos.model.Station;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        List<Station> stations = new ArrayList<Station>();
        Station a = new Station("A");
        Station b = new Station("B");
        Station c = new Station("C");
        Station d = new Station("D");
        Station e = new Station("E");
        stations.add(a);
        stations.add(b);
        stations.add(c);
        stations.add(d);
        stations.add(e);

        List<Route> routes = new ArrayList<Route>();
        routes.add(new Route(a,b,3));
        routes.add(new Route(b,a,3));
        routes.add(new Route(b,c,7));
        routes.add(new Route(a,d,6));
        routes.add(new Route(c,d,8));
        routes.add(new Route(d,e,9));
        routes.add(new Route(e,d,9));
        routes.add(new Route(d,c,9));
        routes.add(new Route(d,b,5));
        routes.add(new Route(c,e,3));

        Scanner scanner = new Scanner(System.in);
        System.out.println("Provide the mame of station you want to start travelling from");
        Station beginningStation =  new Station(scanner.next());
        while(!stations.contains(beginningStation)){
            System.out.println("Provided station name is not proper");
            beginningStation = new Station(scanner.next());
        }
        RouteFinder routeFinder = new RouteFinder(routes);
        routeFinder.calculateRoutesFromSation(beginningStation);
        System.out.println("Where do you want to travel ? ");
        Station destinationStation = new Station(scanner.next());
        List<Station> totalRoute = routeFinder.getQuickestTravelRoute(destinationStation);
        System.out.println("The shortest route to your station is:" + totalRoute);
        System.out.println("Estimated travel time is: " + routeFinder.getTravelTime(totalRoute));

    }


}