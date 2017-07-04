import org.dominwos.finder.RouteFinder;
import org.dominwos.model.Route;
import org.dominwos.model.Station;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
public class DjikstraSimpleTest {
    List<Route> routeList = new ArrayList<>();
    List<Station> stations = new ArrayList<>();

    @Test
    public void testFindsQuickestPath() {
        Station a = new Station("A");
        Station b = new Station("B");
        Station c = new Station("c");
        stations.add(a);
        stations.add(b);
        stations.add(c);
        Route route = new Route(a, c, 10);
        Route route1 = new Route(a, b, 2);
        Route route2 = new Route(b, c, 3);
        routeList.add(route);
        routeList.add(route1);
        routeList.add(route2);

        RouteFinder routeFinder = new RouteFinder(routeList);
        routeFinder.calculateRoutesFromSation(a);
        List<Station> travelRoute = routeFinder.getQuickestTravelRoute(c);
        assertTrue(travelRoute.contains(b));
        assertTrue(travelRoute.contains(c));
        int travelTime = routeFinder.getTravelTime(travelRoute);
        assertEquals(travelTime,5);
    }

    @Test
    public void picksPathWhenTwoAreEqual() {
        Station a = new Station("A");
        Station b = new Station("B");
        Station c = new Station("C");
        Station d = new Station("D");
        stations.add(a);
        stations.add(b);
        stations.add(c);
        Route route = new Route(a, d, 10);
        Route route1 = new Route(a, b, 2);
        Route route2 = new Route(b, c, 3);
        Route route3 = new Route(c, d ,5);

        routeList.add(route);
        routeList.add(route1);
        routeList.add(route2);
        routeList.add(route3);

        RouteFinder routeFinder = new RouteFinder(routeList);
        routeFinder.calculateRoutesFromSation(a);
        List<Station> travelRoute = routeFinder.getQuickestTravelRoute(d);
        assertTrue(travelRoute.contains(d));
        assertEquals(travelRoute.size(),2);
        assertEquals(routeFinder.getTravelTime(travelRoute), 10);
    }

    @Test
    public void returnsEmptyListWhenGraphIsNotClosed() {
        Station a = new Station("A");
        Station b = new Station("B");
        Station c = new Station("C");
        Station d = new Station("D");
        stations.add(a);
        stations.add(b);
        stations.add(c);
        Route route = new Route(a, c, 10);
        Route route1 = new Route(a, b, 2);
        Route route2 = new Route(b, c, 3);
        routeList.add(route);
        routeList.add(route1);
        routeList.add(route2);
        RouteFinder routeFinder = new RouteFinder(routeList);
        routeFinder.calculateRoutesFromSation(a);
        List<Station> travelRoute = routeFinder.getQuickestTravelRoute(d);
        assertEquals(travelRoute.size() , 0);

    }
}
